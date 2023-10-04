package com.example.mapapp.domain.models

data class MarkerEntity(
    var latitude: Double,
    val longitude: Double,
    var name: String,
    var description: String,
    var id: Long = 0,
)