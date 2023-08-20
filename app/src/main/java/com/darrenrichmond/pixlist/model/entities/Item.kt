package com.darrenrichmond.pixlist.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Item (
    @PrimaryKey(autoGenerate = false)
    var itemName: String,
    var itemDescription: String,
    var itemPic: String,
    var itemCreatedDate: String = LocalDate.now().toString(),
    //This is the attribute I am adding to test Room migration
    // in the past I did not tell it what to use for the value on instances I had created in the past
    @ColumnInfo(name = "topFolderName", defaultValue = "none specified")
    val topFolderName: String
)
