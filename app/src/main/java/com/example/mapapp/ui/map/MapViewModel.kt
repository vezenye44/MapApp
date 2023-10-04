package com.example.mapapp.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapapp.domain.models.MarkerEntity
import com.example.mapapp.domain.repository.MarkersRepository
import kotlinx.coroutines.launch

class MapViewModel(
    private val markersRepository: MarkersRepository,
) : ViewModel() {

    private val markersList = mutableListOf<MarkerEntity>()

    private val markerLiveData = MutableLiveData<List<MarkerEntity>>()

    init {
        viewModelScope.launch {
            markersRepository.getMarkersList().also {
                markersList.addAll(it)
                markerLiveData.value = it
            }
        }
    }

    fun markerLiveData(): LiveData<List<MarkerEntity>> {
        return markerLiveData
    }

    fun addMarker(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            val markersEntity = markersRepository.addMarkerByPoint(latitude, longitude)
            markersList.add(markersEntity)
            markerLiveData.value = markersList
        }
    }
}