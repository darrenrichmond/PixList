package com.darrenrichmond.pixlist.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.darrenrichmond.pixlist.dao.ItemDao
import com.darrenrichmond.pixlist.model.entities.Item

@Database(
    entities = [Item::class],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)
abstract class ItemDatabase: RoomDatabase() {

    abstract val dao: ItemDao
}