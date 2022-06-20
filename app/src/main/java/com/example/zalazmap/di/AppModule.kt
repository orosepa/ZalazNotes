package com.example.zalazmap.di

import android.app.Application
import android.content.Context
import androidx.room.Room
//import com.example.zalazmap.data.spot.SpotDatabase
import com.example.zalazmap.domain.model.Station
import com.example.zalazmap.domain.repository.SpotRepository
//import com.example.zalazmap.domain.repository.SpotRepositoryImpl
import com.example.zalazmap.domain.repository.StationRepository
import com.example.zalazmap.domain.repository.StationRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

//    @Singleton
//    @Provides
//    fun provideSpotDatabase(app: Application): SpotDatabase {
//        return Room.databaseBuilder(
//            app,
//            SpotDatabase::class.java,
//            "zalaz_spots.db"
//        ).build()
//    }

//    @OptIn(ExperimentalSerializationApi::class)
    @Singleton
    @Provides
    fun provideStationRepository(@ApplicationContext context: Context): StationRepository {
        return StationRepositoryImpl(context)
    }

//    @Singleton
//    @Provides
//    fun provideSpotRepository(db: SpotDatabase): SpotRepository {
//        return SpotRepositoryImpl(db.dao)
//    }
}