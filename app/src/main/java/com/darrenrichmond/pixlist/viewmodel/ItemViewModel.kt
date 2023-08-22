package com.darrenrichmond.pixlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darrenrichmond.pixlist.dao.ItemDao
import com.darrenrichmond.pixlist.enums.ItemSortType
import com.darrenrichmond.pixlist.events.ItemEvent
import com.darrenrichmond.pixlist.model.entities.Item
import com.darrenrichmond.pixlist.state.ItemState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class ItemViewModel(
    private val dao: ItemDao
): ViewModel() {
    private val _itemSortType = MutableStateFlow(ItemSortType.ITEM_NAME)
    private val _items = _itemSortType
        .flatMapLatest { itemSortType ->
            when(itemSortType) {
                ItemSortType.ITEM_NAME -> dao.getItemsOrderedByName()
                ItemSortType.ITEM_CREATED_DATE -> dao.getItemsOrderedByDate()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _state = MutableStateFlow(ItemState())
    val state = combine(_state, _itemSortType, _items) { state, sortType, items ->
        state.copy(
            items = items,
            itemSortType =  sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ItemState())

    fun onEvent(event: ItemEvent) {
        when(event) {
            is ItemEvent.DeleteItem -> {
                //because deleting an item requires a coroutine
                viewModelScope.launch {
                    dao.deleteItem(event.item)
                }
            }
            ItemEvent.HideNewItemDialog -> {
                //this copies all the state of the event to the new state, but updates just the item I specified.
                _state.update { it.copy(
                    isAddingItem = false
                ) }
            }


            ItemEvent.SaveItem -> {
                val itemName = state.value.itemName
                val itemDescription = state.value.itemDescription
                val itemPic = state.value.itemPic
                val topFolderName = state.value.topFolderName

                //itemName is required
                if(itemName.isBlank()) {
                    return
                }

                //TODO: let's see how it works to store blank values in the database.

                val item = Item(
                    itemName = itemName,
                    itemDescription = itemDescription,
                    itemPic = itemPic,
                    topFolderName = topFolderName
                )
                viewModelScope.launch {
                    dao.insertItem(item)
                }
                _state.update { it.copy(
                    isAddingItem = false,
                    itemName = "",
                    itemDescription = "",
                    itemPic = "",
                    topFolderName = ""
                ) }
            }

            /*
            var itemName: String,
    var itemDescription: String,
    var itemPic: String,
    //practicing field migration by renaming from itemCreatedDate to itemCreatedDate
    var itemCreatedDate: String = LocalDate.now().toString(),
    //This is the attribute I am adding to test Room migration
    // in the past I did not tell it what to use for the value on instances I had created in the past
    @ColumnInfo(name = "topFolderName", defaultValue = "none specified")
    val topFolderName: String

             */

            is ItemEvent.SetItemDescription -> {
                _state.update { it.copy(
                    itemDescription = event.itemDescription
                ) }
            }
            is ItemEvent.SetItemName -> {
                _state.update { it.copy(
                    itemName = event.itemName
                ) }
            }
            is ItemEvent.SetItemPic -> {
                _state.update { it.copy(
                    itemPic = event.itemPic
                ) }
            }
            is ItemEvent.SetTopFolderName -> {
                _state.update { it.copy(
                    topFolderName = event.topFolderName
                ) }
            }
            ItemEvent.ShowNewItemDialog -> {
                _state.update { it.copy(
                    isAddingItem = true
                ) }
            }
            is ItemEvent.SortItems -> {
                _itemSortType.value = event.itemSortType
            }
        }
    }
}