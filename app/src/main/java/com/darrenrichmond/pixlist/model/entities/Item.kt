package com.darrenrichmond.pixlist.model.entities

import java.time.LocalDate

data class Item (
    var itemName: String,
    var itemDescription: String,
    var itemPic: String,
    var itemCreatedDate: LocalDate = LocalDate.now()
)
