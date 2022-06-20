package com.example.zalazmap

import com.example.zalazmap.domain.model.Station
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Test

import org.junit.Assert.*
import java.io.File

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
    @OptIn(ExperimentalSerializationApi::class)
    @Test
    fun checkPrint() {
        val jsonString = File("src/main/java/com/example/zalazmap/data/station/stations.json").readText()
        println(Json.decodeFromString<List<Station>>(jsonString).size)
    }
}