package io.github.kgooglemap.ui

import io.github.kgooglemap.utils.LatLng

data class CameraPosition(
    val position: LatLng,
    val zoom: Float = 15f,
)
