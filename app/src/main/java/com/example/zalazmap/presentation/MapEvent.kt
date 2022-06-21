package com.example.zalazmap.presentation

import com.example.zalazmap.domain.model.Station

sealed class MapEvent {
    data class OnInfoWindowLongClick(val station: Station): MapEvent()
}
