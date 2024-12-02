package io.github.kgooglemap.ui

import android.location.Location
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.AdvancedMarker
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import io.github.kgooglemap.KMapController
import io.github.kgooglemap.services.MyLocationServices

@OptIn(ExperimentalPermissionsApi::class)
@Composable
actual fun KGoogleMapView(
    controller: KMapController
) {
    val myLocationServices = MyLocationServices()
    val permissionState = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)
    var currentUserLocation by remember { mutableStateOf<Location?>(null) }

    // Request location permission and get the location if granted
    LaunchedEffect(permissionState.status) {
        if (permissionState.status.isGranted) {
            currentUserLocation = myLocationServices.getLocation()
        } else if (permissionState.status.shouldShowRationale || !permissionState.status.isGranted) {
            permissionState.launchPermissionRequest()
        }
        controller.resetCamera()
    }

    // Initialize the camera position state
    val cameraPositionState = rememberCameraPositionState {
        // Default position if no location is available yet
        currentUserLocation?.let {
            position = CameraPosition.fromLatLngZoom(
                LatLng(it.latitude, it.longitude), 10f
            )
        } ?: run {
            position = CameraPosition.fromLatLngZoom(LatLng(0.0, 0.0), 15f)
        }
    }

    LaunchedEffect(Unit) {
        controller.init(cameraPositionState)
    }

    // Update the camera position when currentUserLocation changes
    LaunchedEffect(currentUserLocation) {
        currentUserLocation?.let {
            cameraPositionState.position = CameraPosition.fromLatLngZoom(
                LatLng(it.latitude, it.longitude), 10f
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
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
