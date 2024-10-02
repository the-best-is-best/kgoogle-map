package io.github.kgooglemap

import cocoapods.GoogleMaps.GMSCameraPosition
import cocoapods.KGoogleMap.KMapView
import cocoapods.KGoogleMap.MarkerData
import io.github.kgooglemap.ui.CameraPosition
import io.github.kgooglemap.utils.toMarkerData
import io.github.kgooglemap.utils.LatLng
import io.github.kgooglemap.utils.Markers
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.memScoped
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.Foundation.NSNumber
import platform.posix.boolean_t

@OptIn(ExperimentalForeignApi::class)
actual class KMapController actual constructor(camera: CameraPosition , markers: List<Markers>?) {
    private var mapView: KMapView? = null
     val markers: List<MarkerData>? = if(markers.isNullOrEmpty()) null
    else markers.toMarkerData()
     val initCamera = camera.let {
         GMSCameraPosition.cameraWithLatitude(it.position.latitude, it.position.longitude, it.zoom)


     }


    internal fun init(mapView: KMapView) {
        this.mapView = mapView
        mapView.resetCameraPosition()
        println("mapView initialized: true")
    }

    actual fun renderRoad(points: String) {
        println("render road and map is: ${mapView != null} and points: $points")
        mapView?.renderRoad(points)
    }

    actual fun resetCamera() {
        mapView?.resetCameraPosition() // Ensure this method exists in your KMapView implementation
    }

    actual fun addMarkers(markers: List<Markers>) {
        mapView?.updateMarkers(markers.toMarkerData())
    }

    actual fun clearMarkers() {
        mapView?.clearMarkers()

    }

    actual fun goToLocation(location: LatLng , zoom:Float) {
        val coordinate = memScoped {
            CLLocationCoordinate2DMake(location.latitude, location.longitude)
        }
        mapView?.zoomToLocation(coordinate, zoom)
    }

   actual fun showLocationUser(show:Boolean){
       println("show user $show")
        mapView?.showUserLocation(show)
    }

  actual  fun showRoad(show:Boolean){
        mapView?.setRouteVisibility(show)
    }
}
