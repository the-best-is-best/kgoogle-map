package io.github.kgooglemap

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import com.google.maps.android.compose.CameraPositionState
import io.github.kgooglemap.services.MyLocationServices
import io.github.kgooglemap.ui.CameraPosition
import io.github.kgooglemap.utils.LatLng
import io.github.kgooglemap.utils.Markers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import com.google.android.gms.maps.model.LatLng as GMapLatLng

actual class KMapController actual constructor(camera: CameraPosition?, markers: List<Markers>?) {
    private var accessCamera = camera


    // Wait until the location is available before creating the initial camera position


    private var zoom = if (camera == null) 15f else camera.zoom + 5
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
                    getCameraPosition().target,
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
    private suspend fun getCameraPosition(): com.google.android.gms.maps.model.CameraPosition {
        return if (accessCamera?.position != null) {
            com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(
                com.google.android.gms.maps.model.LatLng(
                    accessCamera!!.position!!.latitude, accessCamera!!.position!!.longitude
                ), accessCamera!!.zoom
            )
        } else {
            val location = MyLocationServices().getLocation()
            if (location != null) {
                com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(
                    com.google.android.gms.maps.model.LatLng(
                        location.latitude,
                        location.longitude
                    ),
                    accessCamera?.zoom ?: 15f
                )
            } else {
                com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(
                    com.google.android.gms.maps.model.LatLng(
                        0.0,
                        0.0
                    ),
                    accessCamera?.zoom ?: 15f
                )
            }
        }
    }

    actual fun renderRoad(points: String) {
        if (points.isEmpty() || points.length < 2) {
            // Handle invalid or empty points string
            Log.e("renderRoad", "Invalid points string")
            return
        }

        try {
            val decodedPoints = PolyUtil.decode(points)

            // Update polyline options with the parsed points
            polylineOptions = PolylineOptions()
                .addAll(decodedPoints.map { GMapLatLng(it.latitude, it.longitude) })
                .width(10f)
                .color(android.graphics.Color.BLUE) // Choose your desired color
        } catch (e: Exception) {
            Log.e("renderRoad", "Error decoding points string", e)
        }
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
