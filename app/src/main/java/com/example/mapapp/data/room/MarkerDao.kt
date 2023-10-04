package com.example.mapapp.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface MarkerDao {

    @Query("SELECT * FROM Marker")
    suspend fun getMarker(): List<MarkerDatabaseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarker(markerEntity: MarkerDatabaseEntity): Long

    @Update
    suspend fun updateMarker(markerEntityList: MarkerDatabaseEntity)

    @Update
    suspend fun updateMarker(markerEntityList: List<MarkerDatabaseEntity>)

    @Delete
    suspend fun deleteMarker(markerEntity: MarkerDatabaseEntity)

}