package io.github.kgooglemap

import io.github.kgooglemap.ui.CameraPosition
import io.github.kgooglemap.utils.LatLng
import io.github.kgooglemap.utils.Markers

expect class KMapController(camera: CameraPosition? = null ,  markers: List<Markers>? = null) {
     fun resetCamera()
     fun fetchRoad(from: LatLng?= null, to: LatLng)

}

