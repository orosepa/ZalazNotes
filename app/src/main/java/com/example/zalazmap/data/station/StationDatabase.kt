package com.example.zalazmap.data.station

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [StationEntity::class],
    version = 1
)
abstract class StationDatabase: RoomDatabase() {
//    abstract fun stationDao: StationDao
    abstract val dao: StationDao

    companion object {
        private var instance: StationDatabase? = null

        fun getDatabase(context: Context): StationDatabase? {
            if (instance == null) {
                synchronized(StationDatabase::class.java){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        StationDatabase::class.java,
                        "stations.db"
                    )
                        .addCallback(StartingStations(context))
                        .build()
                }
            }
            return instance
        }
    }
}
