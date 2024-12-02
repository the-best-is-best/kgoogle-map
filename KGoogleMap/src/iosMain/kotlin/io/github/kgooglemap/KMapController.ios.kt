package io.github.kgooglemap

import cocoapods.GoogleMaps.GMSCameraPosition
import cocoapods.KGoogleMap.KMapView
import cocoapods.KGoogleMap.MarkerData
import io.github.kgooglemap.services.LocationService
import io.github.kgooglemap.ui.CameraPosition
import io.github.kgooglemap.utils.LatLng
import io.github.kgooglemap.utils.Markers
import io.github.kgooglemap.utils.toMarkerData
import kotlinx.cinterop.memScoped
import platform.CoreLocation.CLLocationCoordinate2DMake

actual class KMapController actual constructor(camera: CameraPosition?, markers: List<Markers>?) {
    private var mapView: KMapView? = null
    private var userLocation: Pair<Double, Double>? = null
    internal val markers: List<MarkerData>? =
        if (markers.isNullOrEmpty()) null else markers.toMarkerData()
    private val zoom = if (camera?.zoom != null) camera.zoom + 5 else 15f
    internal suspend fun init(mapView: KMapView) {
        this.mapView = mapView

        val location = LocationService().getCurrentLocation()
        userLocation = location
        mapView.setCameraPosition(
            GMSCameraPosition(
                location?.first ?: 0.0,
                location?.second ?: 0.0,
                zoom
            ) as objcnames.classes.GMSCameraPosition
        )
        println("mapView initialized: true")
    }

    actual fun renderRoad(points: String) {
        mapView?.renderRoad(points)
    }

    actual fun resetCamera() {
        mapView?.setCameraPosition(
            GMSCameraPosition(
                userLocation?.first ?: 0.0,
                userLocation?.second ?: 0.0,
                zoom
            ) as objcnames.classes.GMSCameraPosition
        )

    }

    actual fun addMarkers(markers: List<Markers>) {
        mapView?.updateMarkers(markers.toMarkerData())
    }

    actual fun clearMarkers() {
        mapView?.clearMarkers()
    }

    actual fun goToLocation(location: LatLng, zoom: Float) {
        val coordinate = memScoped {
            CLLocationCoordinate2DMake(location.latitude, location.longitude)
        }
        mapView?.zoomToLocation(coordinate, zoom)
    }

    actual fun showLocationUser(show: Boolean) {
        println("show user $show")
        mapView?.showUserLocation(show)
    }

    actual fun showRoad(show: Boolean) {
        mapView?.setRouteVisibility(show)
    }
}
