package com.example.zalazmap.data.station

import android.content.Context
import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.zalazmap.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class StartingStations(
    private val context: Context
) : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        CoroutineScope(Dispatchers.IO).launch {
            loadStations(context)
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    private suspend fun loadStations(
        context: Context
    ) {

        val dao = StationDatabase.getDatabase(context)?.dao

        val jsonData = context.resources.openRawResource(
            R.raw.stations
        ).bufferedReader().use { it.readText() }

        Json.decodeFromString<List<StationEntity>>(jsonData).filter {
            it.direction != "МЦК: Московское центральное кольцо"
                    && it.direction != "Московский монорельс"
                    && it.direction != ""
        }.forEach {
            try {
                dao?.insertStation(it)
            } catch (e: Exception) {
                Log.d("Insert", "Error")
            }
        }
    }

}