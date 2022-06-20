package com.example.zalazmap.domain.repository

import com.example.zalazmap.domain.model.Station

interface StationRepository {
    fun getAllStations(): List<Station>
}