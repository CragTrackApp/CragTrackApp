package com.blockbasti.cragtrackapp.db.classes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Location(
    @PrimaryKey val placeId : String, // Google Place ID or LatLong
    val name: String?,
    val lat: Double?,
    val long: Double?
)