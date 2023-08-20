package com.darrenrichmond.pixlist

import androidx.compose.foundation.Image
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.darrenrichmond.pixlist.model.entities.Item
import com.darrenrichmond.pixlist.ui.theme.PixListTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.darrenrichmond.pixlist.db.ItemDatabase
import com.darrenrichmond.pixlist.viewmodel.ItemViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<ItemViewModel>()
    private var ebayItems: List<Item> = emptyList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //create the item DB where I store all the Items created.
        val db = Room.databaseBuilder(
            applicationContext,
            ItemDatabase::class.java,
            "items.db"
        ).build()

        lifecycleScope.launch {
            ebayItems = db.dao.getItems()
            ebayItems.forEach(::println)
        }

        /*
        (1..4).forEach {
            //had to add the launch wrapper due to insertItem is a suspend function
            //https://stackoverflow.com/questions/53928668/suspend-function-callgetapi-should-be-called-only-from-a-coroutine-or-another
            viewModel.viewModelScope.launch {
                db.dao.insertItem(
                    Item(
                        itemName = "ebayItem_$it",
                        itemDescription = "this is a really really really really really long description.",
                        itemPic = "bailey"
                    )
                )
            }
        }

         */

        setContent {
            PixListTheme {

                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {}
                Column {
                    //this is what I had with just sample data
                    ItemsList(SampleItemData.itemsSample)
                    //trying to get the list from the DB
                   // ItemsList(ebayItems)

                    Button(onClick = {
                        viewModel.newItem("name",
                            "description",
                            "bucket")
                    }) {
                        Text("New Item")
                    }
                }
            }


        }


    }
}

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

@Composable
fun ItemsList(itemEntities: List<Item>) {
    LazyColumn {
        items(itemEntities) { itemEntity ->
            ItemCard(itemEntity)
        }
    }
}

@Preview
@Composable
fun ItemsList() {
    LazyColumn {
        items(SampleItemData.itemsSample) { itemEntity ->
            ItemCard(itemEntity)
        }
    }
}

