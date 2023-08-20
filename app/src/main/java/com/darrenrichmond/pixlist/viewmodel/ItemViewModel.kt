package com.darrenrichmond.pixlist.viewmodel

import androidx.lifecycle.ViewModel


class ItemViewModel: ViewModel() {

    fun newItem(
        itemName: String,
        itemDescription: String,
        itemPic: String
    ) {
        println("Clicked 'New Item'")
    }
}