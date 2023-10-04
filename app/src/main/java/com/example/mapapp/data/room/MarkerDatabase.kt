package com.example.mapapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MarkerDatabaseEntity::class], version = 1, exportSchema = false)
abstract class MarkerDatabase : RoomDatabase() {
    abstract fun getMarketDao(): MarkerDao
}