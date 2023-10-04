package com.example.mapapp.data.location_provider

import android.location.Location

interface LocationProvider {
    fun getLocation(): Location?
}