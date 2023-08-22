package com.darrenrichmond.pixlist.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import com.darrenrichmond.pixlist.dao.ItemDao
import com.darrenrichmond.pixlist.model.entities.Item

@Database(
    entities = [Item::class],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
//        AutoMigration(from = 2, to = 3, spec = ItemDatabase.Migrate2to3::class)
    ]
)
abstract class ItemDatabase: RoomDatabase() {

    abstract val dao: ItemDao

//    @RenameColumn(tableName = "Item", fromColumnName = "itemCreatedDate", toColumnName = "itemCreatedDate")
//    class Migrate2to3: AutoMigrationSpec
}