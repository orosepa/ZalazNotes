package com.example.zalazmap.di

import android.app.Application
import androidx.room.Room
import com.example.zalazmap.data.station.StationDatabase
import com.example.zalazmap.domain.repository.StationRepository
import com.example.zalazmap.domain.repository.StationRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideStationDatabase(app: Application): StationDatabase {
        return StationDatabase.getDatabase(app.applicationContext) ?:
        Room.databaseBuilder(
            app,
            StationDatabase::class.java,
            "zalaz_spots.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideStationRepository(db: StationDatabase): StationRepository {
        return StationRepositoryImpl(db.dao)
    }
}