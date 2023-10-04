package com.example.mapapp.data.utils

import com.example.mapapp.data.room.MarkerDatabaseEntity
import com.example.mapapp.domain.models.MarkerEntity

object MarkerEntityKonverter {

    fun entityToDatabaseEntity(markerEntity: MarkerEntity): MarkerDatabaseEntity {
        return MarkerDatabaseEntity(
            markerEntity.latitude,
            markerEntity.longitude,
            markerEntity.name,
            markerEntity.description,
            markerEntity.id
        )
    }

    fun databaseEntityToEntity(markerDatabaseEntity: MarkerDatabaseEntity): MarkerEntity {
        return MarkerEntity(
            markerDatabaseEntity.lat,
            markerDatabaseEntity.lng,
            markerDatabaseEntity.name,
            markerDatabaseEntity.description,
            markerDatabaseEntity.id
        )
    }
}
