package com.darrenrichmond.pixlist.state

import com.darrenrichmond.pixlist.enums.ItemSortType
import com.darrenrichmond.pixlist.events.ItemEvent
import com.darrenrichmond.pixlist.model.entities.Item

data class ItemState(
    val items: List<Item> = emptyList(),
    val itemName: String = "",
    val itemDescription: String = "",
    val itemPic: String = "",
    // I'm not adding the state for itemCreatedDate because that state should never change - get from the Item itself as read-onlu
    val topFolderName: String = "",
    val isAddingItem: Boolean = false,
    val itemSortType: ItemSortType = ItemSortType.ITEM_NAME

)
