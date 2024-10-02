package io.github.kgooglemap

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import com.google.maps.android.compose.CameraPositionState
import io.github.kgooglemap.ui.CameraPosition
import io.github.kgooglemap.utils.LatLng
import io.github.kgooglemap.utils.Markers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import com.google.android.gms.maps.model.LatLng as GMapLatLng

actual class KMapController actual constructor(camera: CameraPosition, markers: List<Markers>?) {

    var cameraPosition = com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(
        com.google.android.gms.maps.model.LatLng(
            camera.position.latitude, camera.position.longitude
        ), camera.zoom + 5
    )
    private var zoom = camera.zoom + 5

    private var cameraPositionState: CameraPositionState? = null

    // Public getter for markers
    var currentMarker by mutableStateOf(markers)

    var showUserLocation by mutableStateOf(true)

    var showRoad by mutableStateOf(true)

    var polylineOptions by mutableStateOf(PolylineOptions())

    fun init(cameraPosition: CameraPositionState) {
        this.cameraPositionState = cameraPosition
    }

    actual fun resetCamera() {
        CoroutineScope(Dispatchers.Main + SupervisorJob()).launch {
            cameraPositionState?.animate(
                com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(
                    cameraPosition.target,
                    zoom // Adjust the zoom level as needed
                )
            )
        }
    }

    actual fun addMarkers(markers: List<Markers>) {
        this.currentMarker = markers
    }

    actual fun clearMarkers() {
        currentMarker = listOf()
    }

    actual fun renderRoad(points: String) {
        // Decode the encoded polyline string to a list of LatLng points
//        val pointList = decodePolyline(points)
        val decodedPoints: List<com.google.android.gms.maps.model.LatLng> = PolyUtil.decode(points).toList()

        // Update polyline options with the parsed points
        polylineOptions = PolylineOptions()
            .addAll(decodedPoints.map { GMapLatLng(it.latitude, it.longitude) })
            .width(10f)
            .color(android.graphics.Color.BLUE) // Choose your desired color
    }



    actual fun goToLocation(location: LatLng, zoom: Float) {
        CoroutineScope(Dispatchers.Main + SupervisorJob()).launch {
            cameraPositionState?.animate(
                com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(
                    com.google.android.gms.maps.model.LatLng(
                        location.latitude,
                        location.longitude
                    ),
                    zoom // Adjust the zoom level as needed
                )
            )
        }
    }

    actual fun showLocationUser(show: Boolean) {
        showUserLocation = show
    }

    actual fun showRoad(show: Boolean) {
        showRoad = show
    }
}
