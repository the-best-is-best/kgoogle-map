package io.github.kgooglemap

import io.github.kgooglemap.ui.CameraPosition
import io.github.kgooglemap.utils.LatLng
import io.github.kgooglemap.utils.Markers

actual class KMapController actual constructor(camera: CameraPosition, markers: List<Markers>?) {
    actual fun resetCamera() {
    }

    actual fun fetchRoad(
        from: LatLng?,
        to: LatLng
    ) {
    }

    actual fun addMarkers(markers: List<Markers>) {
    }

    actual fun clearMarkers() {
    }


}