package com.example.zalazmap.presentation.map

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zalazmap.domain.repository.StationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val stationRepository: StationRepository
): ViewModel() {

    var state by mutableStateOf(MapState())

    init {
        viewModelScope.launch {
            viewModelScope.launch {
                stationRepository.getAllStations().collectLatest {  stations ->
                    state = state.copy(
                        stations = stations
                    )
                }
            }
        }
    }

    fun onEvent(event: MapEvent) {
        when (event) {
            is MapEvent.OnSaveStationButtonClick -> {
                viewModelScope.launch {
                    stationRepository.updateStation(event.station)
                }
            }
        }
    }
}