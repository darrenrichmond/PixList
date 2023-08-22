package com.darrenrichmond.pixlist.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.darrenrichmond.pixlist.model.entities.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: Item)

    @Delete
    suspend fun deleteItem(item: Item)

    @Query("SELECT * FROM item")
    fun  getItems(): List<Item>

    @Query("SELECT * FROM item ORDER BY itemName ASC")
    fun getItemsOrderedByName(): Flow<List<Item>>

    @Query("SELECT * FROM item ORDER BY itemCreatedDate ASC")
    fun getItemsOrderedByDate(): Flow<List<Item>>
}