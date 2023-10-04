package com.example.mapapp.ui.markers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapapp.domain.models.MarkerEntity
import com.example.mapapp.domain.repository.MarkersRepository
import kotlinx.coroutines.launch

class MarkersListViewModel(
    private val markersRepository: MarkersRepository,
) : ViewModel() {

    private val markerLiveData = MutableLiveData<List<MarkerEntity>>()

    private var markersList: List<MarkerEntity> = listOf()

    init {
        viewModelScope.launch {
            markersList = markersRepository.getMarkersList()
            markerLiveData.value = markersList
        }
    }


    fun markerLiveData(): LiveData<List<MarkerEntity>> {
        return markerLiveData
    }

    fun updateMarkerName(name: String, markerId: Long) {
        val marker = markersList.first {
            it.id == markerId
        }.apply {
            this.name = name
        }
        viewModelScope.launch {
            markersRepository.updateMarker(marker)
        }
    }

    fun updateMarkerDescription(description: String, markerId: Long) {
        val marker = markersList.first {
            it.id == markerId
        }.apply {
            this.description = description
        }
        viewModelScope.launch {
            markersRepository.updateMarker(marker)
        }
    }
}