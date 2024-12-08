package io.github.kgooglemap

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import com.google.maps.android.compose.CameraPositionState
import io.github.kgooglemap.utils.LatLng
import io.github.kgooglemap.utils.Markers
import io.github.tbib.klocation.KLocationService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import com.google.android.gms.maps.model.LatLng as GMapLatLng

actual class KMapController actual constructor(
    zoom: Float,
    initPosition: LatLng?,
    markers: List<Markers>?
) {


    private val zoom = zoom
    private val initPosition = initPosition
    internal var cameraPositionState: CameraPositionState? = null

    // Public getter for markers
    var currentMarker by mutableStateOf(markers)

    var showUserLocation by mutableStateOf(true)
    var showRoad by mutableStateOf(true)
    var polylineOptions by mutableStateOf(PolylineOptions())

    fun initCamera(cameraPosition: CameraPositionState) {
        this.cameraPositionState = cameraPosition

        if (initPosition != null) {
            CoroutineScope(Dispatchers.Main + SupervisorJob()).launch {

                cameraPositionState?.animate(
                    com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(
                        com.google.android.gms.maps.model.LatLng(
                            initPosition.latitude,
                            initPosition.longitude
                        ),
                        zoom // Adjust the zoom level as needed
                    )
                )
            }
        }

    }

    actual fun resetCamera() {
        CoroutineScope(Dispatchers.Main + SupervisorJob()).launch {
            try {
                val userLocation = KLocationService().getCurrentLocation()
                cameraPositionState?.animate(
                    com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(
                        com.google.android.gms.maps.model.LatLng(
                            userLocation.latitude,
                            userLocation.longitude
                        ),
                        zoom // Adjust the zoom level as needed
                    )
                )
            } catch (e: Exception) {
                println(e)
            }
        }
    }

    actual fun addMarkers(markers: List<Markers>) {
        this.currentMarker = markers
    }

    actual fun clearMarkers() {
        currentMarker = listOf()
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
