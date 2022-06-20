package com.example.zalazmap.data.station

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class StationEntity(
    val direction: String,
    val latitude: Double,
    val longitude: Double,
    val title: String,
    var isProtected: Boolean? = null,
    var isBypassing: Boolean? = null,
    var isExplored: Boolean = false,
    var comment: String? = null,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)