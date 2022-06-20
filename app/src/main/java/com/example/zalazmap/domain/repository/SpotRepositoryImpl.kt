package com.example.zalazmap.domain.repository

//import com.example.zalazmap.data.spot.SpotDao
//import com.example.zalazmap.data.spot.toSpot
//import com.example.zalazmap.data.spot.toSpotEntity
//import com.example.zalazmap.domain.model.Spot
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.map
//
//class SpotRepositoryImpl(
//    private val dao: SpotDao
//) : SpotRepository {
//    override suspend fun insertSpot(spot: Spot) {
//        dao.insertSpot(spot.toSpotEntity())
//    }
//
//    override suspend fun deleteSpot(spot: Spot) {
//        dao.deleteSpot(spot.toSpotEntity())
//    }
//
//    override fun getSpots(): Flow<List<Spot>> {
//        return dao.getSpots().map { spots ->
//            spots.map { it.toSpot() }
//        }
//    }
//}