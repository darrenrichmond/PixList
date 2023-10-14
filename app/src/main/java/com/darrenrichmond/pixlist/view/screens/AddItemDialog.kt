package com.darrenrichmond.pixlist.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.darrenrichmond.pixlist.events.ItemEvent
import com.darrenrichmond.pixlist.state.ItemState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemDialog(
    state: ItemState,
    onEvent: (ItemEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onEvent(ItemEvent.HideNewItemDialog)
        },
        title = { Text(text = "Add Item")},
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                TextField(
                    value = state.itemName,
                    onValueChange = {
                        onEvent(ItemEvent.SetItemName(it))
                    },
                    placeholder = {
                        Text(text = "Item Name")
                    }
                )

                TextField(
                    value = state.itemDescription,
                    onValueChange = {
                        onEvent(ItemEvent.SetItemDescription(it))
                    },
                    placeholder = {
                        Text(text = "Item Description")
                    }
                )

                TextField(
                    value = state.itemPic,
                    onValueChange = {
                        onEvent(ItemEvent.SetItemPic(it))
                    },
                    placeholder = {
                        Text(text = "Item Picture")
                    }
                )

                TextField(
                    value = state.topFolderName,
                    onValueChange = {
                        onEvent(ItemEvent.SetTopFolderName(it))
                    },
                    placeholder = {
                        Text(text = "Item Top Folder Name")
                    }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                onEvent(ItemEvent.SaveItem)
            }) {
                Text(text = "Save")
            }
        }
    )
}