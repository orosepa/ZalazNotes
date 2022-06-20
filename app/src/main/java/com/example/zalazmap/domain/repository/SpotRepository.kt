package com.example.zalazmap.domain.repository

import com.example.zalazmap.domain.model.Spot
import kotlinx.coroutines.flow.Flow

interface SpotRepository {

    suspend fun insertSpot(spot: Spot)

    suspend fun deleteSpot(spot: Spot)

    fun getSpots(): Flow<List<Spot>>
}