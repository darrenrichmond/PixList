package com.darrenrichmond.pixlist.events

import com.darrenrichmond.pixlist.enums.ItemSortType
import com.darrenrichmond.pixlist.model.entities.Item

//handle the user clicking on the screen related to items (trash, create, etc.)
sealed interface ItemEvent {
    object SaveItem: ItemEvent
    data class SetItemName(val itemName: String): ItemEvent
    data class SetItemDescription(val itemDescription: String): ItemEvent
    data class SetItemPic(val itemPic: String): ItemEvent
    // I'm not adding the event for when the user changes the itemCreatedDate because they should never change that
    data class SetTopFolderName(val topFolderName: String): ItemEvent
    object ShowNewItemDialog: ItemEvent
    object HideNewItemDialog: ItemEvent
    data class SortItems(val itemSortType: ItemSortType): ItemEvent
    data class DeleteItem(val item: Item): ItemEvent
}
