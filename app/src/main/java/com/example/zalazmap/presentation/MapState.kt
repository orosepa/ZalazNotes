package com.example.zalazmap.presentation

import com.example.zalazmap.domain.model.Station
import com.google.maps.android.compose.MapProperties

data class MapState(
    var properties: MapProperties = MapProperties(
        isBuildingEnabled = true
    ),
    val stations: List<Station> = emptyList()
)
