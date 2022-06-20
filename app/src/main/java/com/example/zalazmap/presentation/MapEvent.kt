package com.example.zalazmap.presentation

import com.example.zalazmap.domain.model.Station

sealed class MapEvent {
//    data class OnMapLongClick(val latLng: LatLng): MapEvent()
    data class OnInfoWindowLongClick(val station: Station): MapEvent()
//    data class OnStationCircleClick(val station: Station): MapEvent()
}
