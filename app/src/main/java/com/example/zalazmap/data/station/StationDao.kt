package com.example.zalazmap.data.station

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface StationDao {
    @Query("SELECT * FROM stationentity")
    fun getStations(): Flow<List<StationEntity>>

    @Update
    suspend fun updateStation(stationEntity: StationEntity)

    @Insert
    suspend fun insertStation(stationEntity: StationEntity)

    @Query("SELECT * FROM stationentity WHERE id=:id")
    suspend fun findStationById(id: Int): StationEntity
}