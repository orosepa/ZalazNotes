package com.example.zalazmap.presentation.map

import com.example.zalazmap.domain.model.Station

sealed class MapEvent {
    data class OnInfoWindowLongClick(val station: Station): MapEvent()
    data class OnSaveStationButtonClick(val station: Station): MapEvent()
}
