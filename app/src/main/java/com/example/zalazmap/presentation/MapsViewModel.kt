package com.example.zalazmap.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zalazmap.domain.repository.StationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val stationRepository: StationRepository
): ViewModel() {

    var state by mutableStateOf(MapState())

    init {
        viewModelScope.launch {
            state = state.copy(
                stations = stationRepository.getAllStations()
            )
        }
    }

//    fun onEvent(event: MapEvent) {
//        when (event) {
//            is MapEvent.OnMapLongClick -> {
//                viewModelScope.launch {
//                    spotRepository.insertSpot(
//                        Spot(event.latLng.latitude, event.latLng.longitude)
//                    )
//                }
//            }
//            is MapEvent.OnInfoWindowLongClick -> {
//                viewModelScope.launch {
//                    spotRepository.deleteSpot(event.spot)
//                }
//            }
//        }
//    }
}