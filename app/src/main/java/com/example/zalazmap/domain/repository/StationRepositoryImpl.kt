package com.example.zalazmap.domain.repository

import android.content.Context
import com.example.zalazmap.domain.model.Station
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class StationRepositoryImpl(
    private val context: Context
) : StationRepository {
    @OptIn(ExperimentalSerializationApi::class)
    override fun getAllStations(): List<Station> {
        val jsonData = context.applicationContext.resources.openRawResource(
            context.applicationContext.resources.getIdentifier(
                "stations",
                "raw",
                context.applicationContext.packageName
            )
        ).bufferedReader().use { it.readText() }

        return Json.decodeFromString<List<Station>>(jsonData).filter {
            it.direction != "МЦК: Московское центральное кольцо"
                    && it.direction != "Московский монорельс"
                    && it.direction != ""
        }
    }
}