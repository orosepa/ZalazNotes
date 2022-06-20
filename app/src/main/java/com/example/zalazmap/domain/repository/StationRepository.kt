package com.example.zalazmap.domain.repository

import com.example.zalazmap.data.station.StationEntity
import com.example.zalazmap.domain.model.Station
import kotlinx.coroutines.flow.Flow

interface StationRepository {
    fun getAllStations(): Flow<List<Station>>

    suspend fun updateStation(station: Station)

    suspend fun findStationById(id: Int) : Station
}