package com.example.zalazmap.presentation.map

import com.example.zalazmap.domain.model.Station
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.MapProperties

data class MapState(
    var properties: MapProperties = MapProperties(
        mapStyleOptions = MapStyleOptions(MapStyle.json),
        isBuildingEnabled = true
    ),
    val stations: List<Station> = emptyList()
)
