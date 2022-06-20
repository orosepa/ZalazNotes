package com.example.zalazmap.data.station

import com.example.zalazmap.domain.model.Station

fun StationEntity.toStation(): Station {
    return Station(
        direction = direction,
        latitude = latitude,
        longitude = longitude,
        title = title,
        isProtected = isProtected,
        isBypassing = isBypassing,
        isExplored = isExplored,
        comment = comment,
        id = id
    )
}

fun Station.toStationEntity(): StationEntity {
    return StationEntity(
        direction = direction,
        latitude = latitude,
        longitude = longitude,
        title = title,
        isProtected = isProtected,
        isBypassing = isBypassing,
        isExplored = isExplored,
        comment = comment,
        id = id
    )
}