package com.example.zalazmap.data.spot

import androidx.room.*
import kotlinx.coroutines.flow.Flow

//@Dao
//interface SpotDao {
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertSpot(spot: SpotEntity)
//
//    @Delete
//    suspend fun deleteSpot(spot: SpotEntity)
//
//    @Query("SELECT * FROM spotentity")
//    fun getSpots(): Flow<List<SpotEntity>>
//}