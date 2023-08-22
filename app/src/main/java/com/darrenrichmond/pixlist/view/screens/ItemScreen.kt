package com.darrenrichmond.pixlist.view.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darrenrichmond.pixlist.enums.ItemSortType
import com.darrenrichmond.pixlist.events.ItemEvent
import com.darrenrichmond.pixlist.state.ItemState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemScreen(
    state: ItemState,
    onEvent: (ItemEvent) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(ItemEvent.ShowNewItemDialog)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Item")
            }
        },
        modifier = Modifier.padding(15.dp)
    ) {padding ->
        if(state.isAddingItem) {
            AddItemDialog(state = state, onEvent = onEvent)
        }
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            //this is an item in the LazyColumn, not one of my eBay items
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = CenterVertically
                ) {
                    ItemSortType.values().forEach { sortType ->
                        Row(
                            modifier = Modifier
                                .clickable {
                                    onEvent(ItemEvent.SortItems(sortType))
                                },
                            verticalAlignment = CenterVertically
                        ) {
                            RadioButton(
                                selected = state.itemSortType == sortType,
                                onClick = {
                                    onEvent(ItemEvent.SortItems(sortType))
                                }
                            )
                            Text(text = sortType.name)
                        }
                    }
                }
            }
            //again, this is a LazyColumn items. My eBayItems are being shown as items in the column
            items(state.items) { item ->
                Row (
                    modifier = Modifier.fillMaxWidth()
                ){
                    Column(
                        modifier = Modifier.weight(1f)
                    ){
                        Text(
                            text = "${item.itemName}",
                            fontSize = 20.sp
                        )
                        Text(
                            text = "${item.itemDescription}",
                            fontSize = 12.sp
                        )
                    }
                    IconButton(onClick = {
                        onEvent(ItemEvent.DeleteItem(item))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Item")
                    }
                }
            }
        }
        
    }
}