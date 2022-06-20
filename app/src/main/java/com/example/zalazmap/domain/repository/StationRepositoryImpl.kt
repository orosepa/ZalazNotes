package com.example.zalazmap.domain.repository

import com.example.zalazmap.data.station.StationDao
import com.example.zalazmap.data.station.StationEntity
import com.example.zalazmap.data.station.toStation
import com.example.zalazmap.data.station.toStationEntity
import com.example.zalazmap.domain.model.Station
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StationRepositoryImpl(
    private val dao: StationDao
) : StationRepository {
    override fun getAllStations(): Flow<List<Station>> {
        return dao.getStations().map { stations ->
            stations.map { it.toStation() }
        }
    }

    override suspend fun updateStation(station: Station) {
        dao.updateStation(station.toStationEntity())
    }

    override suspend fun findStationById(id: Int): Station {
        return dao.findStationById(id).toStation()
    }


}