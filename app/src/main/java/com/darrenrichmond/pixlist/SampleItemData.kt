package com.darrenrichmond.pixlist

import com.darrenrichmond.pixlist.model.entities.Item

object SampleItemData {
    val itemsSample = listOf(
        Item(
            itemName = "Fluffy Critter",
            itemDescription = "That critter is pretty fluffy. Oh my goodness, I could just die.",
            itemPic ="bailey",
            topFolderName = "folder_1"
        ),
        Item(
            itemName = "Sad Clown",
            itemDescription = "Boo hoo.",
            itemPic = "bucket",
            topFolderName = "folder_2"
        )
    )
}