package com.example.mapapp.domain.repository

import com.example.mapapp.domain.models.MarkerEntity
import kotlinx.coroutines.flow.Flow

interface MarkersRepository {

    suspend fun addMarker(markerEntity: MarkerEntity)

    suspend fun updateMarker(markerEntity: MarkerEntity)

    suspend fun getAllMarkers(): Flow<List<MarkerEntity>>

    suspend fun addMarkerByPoint(latitude: Double, longitude: Double): MarkerEntity

    suspend fun getMarkersList(): List<MarkerEntity>
}