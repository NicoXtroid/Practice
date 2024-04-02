package com.example.demo.geolocation.ui.components

import android.location.Location

fun getDistance(
    locationLatitude: Double,
    locationLongitude: Double,
    actualLatitude: Double,
    actualLongitude: Double,
): Float {
    val location = Location("")
    location.latitude = locationLatitude
    location.longitude = locationLongitude

    val actualPosition = Location("")
    actualPosition.latitude = actualLatitude
    actualPosition.longitude = actualLongitude

    return location.distanceTo(actualPosition)
}

fun userInRange(
    locationLatitude: Double,
    locationLongitude: Double,
    actualLatitude: Double,
    actualLongitude: Double,
    range: Int = 50
): Boolean {
    return getDistance(locationLatitude, locationLongitude,actualLatitude,actualLongitude) <= range
}

data class GeoLocation(
    val latitude: Double,
    val longitude: Double
)