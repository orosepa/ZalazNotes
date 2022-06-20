package com.example.zalazmap.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Station(
    val direction: String,
    val latitude: Double,
    val longitude: Double,
    val title: String,
    var isProtected: Boolean = false,
    var isBypassing: Boolean = false,
    var comment: String = ""
)