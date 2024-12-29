package io.github.kgooglemap.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.AdvancedMarker
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import io.github.kgooglemap.KMapController
import io.github.tbib.klocation.KLocationService
import io.github.tbib.klocation.Location
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
actual fun KGoogleMapView(
    controller: KMapController,
    onMapLoaded: (() -> Unit)?,
    onMapClick: ((io.github.kgooglemap.utils.LatLng) -> Unit)?,
    onMapLongClick: ((io.github.kgooglemap.utils.LatLng) -> Unit)?
) {
    var currentUserLocation by remember { mutableStateOf<Location?>(null) }
    val cameraPositionState = rememberCameraPositionState { position }
    val coroutineScope = rememberCoroutineScope()
    val locationService = KLocationService()

    LaunchedEffect(controller.cameraPositionState == null) {
        controller.initCamera(cameraPositionState)
    }


    var isCameraMoved by remember { mutableStateOf(false) } // Flag to track if the camera has moved


    LaunchedEffect(Unit) {
        locationService.gpsStateFlow().collect { gps ->
            if (gps) {
                coroutineScope.launch {
                    locationService.startLocationUpdates().collect { newLocation ->
                        if (currentUserLocation != newLocation) {
                            currentUserLocation = newLocation
                            // Move the camera only if it hasn't been moved already
                            if (!isCameraMoved) {
                                cameraPositionState.move(
                                    CameraUpdateFactory.newLatLngZoom(
                                        LatLng(newLocation.latitude, newLocation.longitude),
                                        15f // Zoom level (adjust as needed)
                                    )
                                )
                                isCameraMoved =
                                    true // Set flag to true after the first move
                            }
                        }
                    }
                }
            } else {
                locationService.enableLocation()
                Log.e(
                    "KGoogleMapView",
                    "Location services are disabled or permission not granted"
                )
                // Optionally, show a UI alert or Toast to notify the user.
            }
        }

    }

    // Render the map with user location and other overlays
    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapLoaded = onMapLoaded,
            onMapClick = {
                val latLng =
                    io.github.kgooglemap.utils.LatLng(it.latitude, it.longitude)
                onMapClick?.invoke(latLng)
            },
            onMapLongClick = {
                val latLng =
                    io.github.kgooglemap.utils.LatLng(it.latitude, it.longitude)
                onMapLongClick?.invoke(latLng)

            }
        ) {
            // Display user location if it exists and permission is granted
            if (controller.showUserLocation && currentUserLocation != null) {
                Circle(
                    center = LatLng(
                        currentUserLocation!!.latitude,
                        currentUserLocation!!.longitude
                    ),
                    radius = 40.0, // 40 meters
                    fillColor = Color.Blue.copy(alpha = .5f), // Blue with transparency
                    strokeColor = Color.Blue,
                    strokeWidth = 2f
                )
            }

            // Draw polyline if specified
            if (controller.polylineOptions.points.isNotEmpty() && controller.showRoad) {
                Polyline(
                    points = controller.polylineOptions.points,
                    color = Color(controller.polylineOptions.color),
                    width = controller.polylineOptions.width
                )
            }

            // Display markers
            controller.currentMarker?.forEach { marker ->
                AdvancedMarker(
                    state = MarkerState(
                        position = LatLng(
                            marker.latLng.latitude,
                            marker.latLng.longitude
                        )
                    ),
                    title = marker.title,
                    snippet = marker.snippet
                )
            }
        }
    }
}
