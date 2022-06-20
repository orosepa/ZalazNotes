package com.example.zalazmap.presentation

import com.example.zalazmap.domain.model.Spot
import com.example.zalazmap.domain.model.Station
import com.google.android.gms.maps.model.LatLng

sealed class MapEvent {
    data class OnMapLongClick(val latLng: LatLng): MapEvent()
    data class OnInfoWindowLongClick(val spot: Spot): MapEvent()
    data class OnStationCircleClick(val station: Station): MapEvent()
}
