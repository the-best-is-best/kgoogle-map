package io.github.kgooglemap

import io.github.kgooglemap.utils.LatLng
import io.github.kgooglemap.utils.Markers
//import io.github.kgooglemap.utils.toMarkerData
import io.github.tbib.klocation.KLocationService
import kotlinx.cinterop.memScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import platform.CoreLocation.CLLocationCoordinate2DMake
//import io.github.native.kgooglemap.KMapView
//import io.github.native.kgooglemap.MarkerData

actual class KMapController actual constructor(
    zoom: Float,
    initPosition: LatLng?,
    markers: List<Markers>?
) {
//    private var mapView: KMapView? = null
//    internal val markers: List<MarkerData>? =
//        if (markers.isNullOrEmpty()) null else markers.toMarkerData()
    val zoom = zoom
    private val initPosition: LatLng? = initPosition

//    internal fun init(mapView: KMapView) {
//        this.mapView = mapView
//        CoroutineScope(Dispatchers.Main + SupervisorJob()).launch {
//
//
//            if (initPosition != null) {
//                try {
//                    mapView.setCameraPosition(
//                        GMSCameraPosition(
//                            initPosition.latitude,
//                            initPosition.longitude,
//                            zoom
//                        ) as cocoapods.KGoogleMap.GMSCameraPosition
//                    )
//                } catch (e: Exception) {
//                    println(e)
//                }
//            }
//        }

  //  }


    actual fun renderRoad(points: String) {
       // mapView?.renderRoad(points)
    }

    actual fun resetCamera() {
        CoroutineScope(Dispatchers.Main + SupervisorJob()).launch {
            try {
                val userLocation = KLocationService().getCurrentLocation()
//                mapView?.setCameraPosition(
//                    GMSCameraPosition(
//                        userLocation.latitude,
//                        userLocation.longitude,
//                        zoom
//                    ) as cocoapods.KGoogleMap.GMSCameraPosition
               // )
            } catch (e: Exception) {
                println(e)
            }
        }


    }

    actual fun addMarkers(markers: List<Markers>) {
       // mapView?.updateMarkers(markers.toMarkerData())
    }

    actual fun clearMarkers() {
     //   mapView?.clearMarkers()
    }

    actual fun goToLocation(location: LatLng, zoom: Float) {
        val coordinate = memScoped {
            CLLocationCoordinate2DMake(location.latitude, location.longitude)
        }
     //   mapView?.zoomToLocation(coordinate, zoom)
    }

    actual fun showLocationUser(show: Boolean) {
        println("show user $show")
      //  mapView?.showUserLocation(show)
    }

    actual fun showRoad(show: Boolean) {
        // mapView?.setRouteVisibility(show)
    }
}
