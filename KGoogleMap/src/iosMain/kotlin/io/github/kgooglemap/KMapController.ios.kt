package io.github.kgooglemap

import cocoapods.GoogleMaps.GMSCameraPosition
import cocoapods.KGoogleMap.KMapView
import cocoapods.KGoogleMap.MarkerData
import io.github.kgooglemap.ui.CameraPosition
import io.github.kgooglemap.ui.toMarkerData
import io.github.kgooglemap.utils.LatLng
import io.github.kgooglemap.utils.Markers
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSNumber

@OptIn(ExperimentalForeignApi::class)
actual class KMapController actual constructor(camera: CameraPosition? , markers: List<Markers>?) {
    private var mapView: KMapView? = null
     val markers: List<MarkerData>? = if(markers.isNullOrEmpty()) null
    else markers.toMarkerData()
     val initCamera: GMSCameraPosition? = camera?.let {
        GMSCameraPosition.cameraWithLatitude(it.position.latitude, it.position.longitude, it.zoom)
    }

    internal fun init(mapView: KMapView) {
        this.mapView = mapView

        println("mapView initialized: true")
    }

    actual fun fetchRoad(from: LatLng?, to: LatLng) {
        println("fetch road")
        val fromLat = from?.latitude?.let { NSNumber(it) }
        val fromLng = from?.longitude?.let { NSNumber(it) }
        mapView?.fetchRouteWithStartLat(
            startLat = fromLat,
            startLong = fromLng,
            endLat = NSNumber(to.latitude),
            endLong = NSNumber(to.longitude)
        )
    }

    actual fun resetCamera() {
        mapView?.resetCameraPosition() // Ensure this method exists in your KMapView implementation
    }
}
