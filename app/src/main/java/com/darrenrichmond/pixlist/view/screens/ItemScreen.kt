package com.darrenrichmond.pixlist.view.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darrenrichmond.pixlist.R
import com.darrenrichmond.pixlist.enums.ItemSortType
import com.darrenrichmond.pixlist.events.ItemEvent
import com.darrenrichmond.pixlist.model.entities.Item
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
                    Image(
                        painter = painterResource(R.drawable.bucket),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    var isExpanded:Boolean by remember { mutableStateOf(false) }

                    val surfaceColor by animateColorAsState(
                        if(isExpanded)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.surface
                    )


                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                isExpanded = !isExpanded
                            }
                    ){
                        Text(
                            text = "${item.itemName}",
                            color = MaterialTheme.colorScheme.secondary,
                            style = MaterialTheme.typography.titleSmall
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        Surface (
                            shape = MaterialTheme.shapes.medium,
                            shadowElevation = 10.dp,
                            color = surfaceColor,
                            modifier = Modifier
                                .animateContentSize()
                                .padding(1.dp)
                        ) {
                            Text(
                                text = "${item.itemDescription}",
                                modifier = Modifier.padding(all = 5.dp),
                                maxLines = if(isExpanded) Int.MAX_VALUE else 1,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
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

//old stuff
@Composable
fun ItemCard(item: Item) {
    var dog = 0
    if (item.itemPic == "bucket") {
        dog = R.drawable.bucket
    } else {
        dog = R.drawable.bailey
    }
    println("${item.itemName}: created ${item.itemCreatedDate}")
    println("dog: $dog")

    Row(
        modifier = Modifier.padding(all = 10.dp)
    ) {
        Image(
            painter = painterResource(dog),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )

        Spacer(modifier = Modifier.width(10.dp))

        var isExpanded:Boolean by remember { mutableStateOf(false) }

        val surfaceColor by animateColorAsState(
            if(isExpanded)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.surface
        )

        Column(
            modifier = Modifier.clickable {
                isExpanded = !isExpanded
            }
        ) {
            Text(
                item.itemName,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall
            )

            Spacer(modifier = Modifier.height(5.dp))

            Surface (
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 10.dp,
                color = surfaceColor,
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Text(
                    text = item.itemDescription,
                    modifier = Modifier.padding(all = 5.dp),
                    maxLines = if(isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
