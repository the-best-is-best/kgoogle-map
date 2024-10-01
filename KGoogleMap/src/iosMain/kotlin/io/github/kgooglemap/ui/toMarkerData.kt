package io.github.kgooglemap.ui

import cocoapods.KGoogleMap.MarkerData
import io.github.kgooglemap.utils.Markers
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
fun List<Markers>.toMarkerData(): List<MarkerData> {
    return this.map { marker ->
        MarkerData(
            latitude = marker.latLng.latitude, // Assuming you have latitude in LatLng
            longitude = marker.latLng.longitude, // Assuming you have longitude in LatLng
            title = marker.title,
            snippet = marker.snippet,
            icon = null // Set to your custom icon if needed
        )
    }
}
