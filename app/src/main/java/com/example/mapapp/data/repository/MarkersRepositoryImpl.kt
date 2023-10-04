package com.example.mapapp.data.repository

import com.example.mapapp.data.room.MarkerDatabase
import com.example.mapapp.data.utils.MarkerEntityKonverter.databaseEntityToEntity
import com.example.mapapp.data.utils.MarkerEntityKonverter.entityToDatabaseEntity
import com.example.mapapp.domain.models.MarkerEntity
import com.example.mapapp.domain.repository.MarkersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class MarkersRepositoryImpl(
    private val markerDataBase: MarkerDatabase,
) : MarkersRepository {

    private var markerEntityList: MutableList<MarkerEntity> = mutableListOf()
    private val markerEntityFlow = MutableStateFlow<List<MarkerEntity>>(markerEntityList)

    override suspend fun addMarker(markerEntity: MarkerEntity) {
        markerDataBase.getMarketDao().insertMarker(
            entityToDatabaseEntity(markerEntity)
        ).also {
            markerEntityList.add(markerEntity.apply { id = it })
            markerEntityFlow.value = markerEntityList
        }
    }

    override suspend fun updateMarker(markerEntity: MarkerEntity) {
        markerEntityFlow.value = markerEntityList
        markerDataBase.getMarketDao().updateMarker(
            entityToDatabaseEntity(markerEntity)
        )
    }

    override suspend fun getAllMarkers(): Flow<List<MarkerEntity>> {
        markerDataBase.getMarketDao().getMarker().apply {
            markerEntityList = this.map {
                databaseEntityToEntity(it)
            }.toMutableList()
            markerEntityFlow.value = markerEntityList
        }
        return markerEntityFlow
    }

    override suspend fun getMarkersList(): List<MarkerEntity> {
        return markerDataBase.getMarketDao().getMarker().map {
            databaseEntityToEntity(it)
        }
    }

    override suspend fun addMarkerByPoint(latitude: Double, longitude: Double): MarkerEntity {
        val markerEntity = MarkerEntity(
            latitude,
            longitude,
            "Маркер $latitude : $longitude",
            ""
        )
        val markersId = markerDataBase.getMarketDao().insertMarker(
            entityToDatabaseEntity(markerEntity)
        )

        return markerEntity.apply {
            id = markersId
        }
    }
}