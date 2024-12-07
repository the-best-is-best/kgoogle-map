package io.github.kgooglemap.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.os.Build
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
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
    controller: KMapController
) {
    var currentUserLocation by remember { mutableStateOf<Location?>(null) }
    val context = LocalContext.current
    val cameraPositionState = rememberCameraPositionState { position }
    val coroutineScope = rememberCoroutineScope()
    val locationService = KLocationService()

    LaunchedEffect(controller.cameraPositionState == null) {
        controller.initCamera(cameraPositionState)
    }

    val locationPermissionState = rememberPermissionState(
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )
    var isCameraMoved by remember { mutableStateOf(false) } // Flag to track if the camera has moved


    // Register BroadcastReceiver to listen for location service changes
    DisposableEffect(context) {
        val locationReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent?) {
                if (intent != null && intent.action == LocationManager.PROVIDERS_CHANGED_ACTION) {
                    if (locationService.isLocationEnabled() && locationPermissionState.status.isGranted) {
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
                        Log.e(
                            "KGoogleMapView",
                            "Location services are disabled or permission not granted"
                        )
                        // Optionally, show a UI alert or Toast to notify the user.
                    }
                }
            }
        }

        // Registering receiver for Android O and newer
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context.registerReceiver(
                    locationReceiver,
                    IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION),
                    Context.RECEIVER_NOT_EXPORTED
                )
            }
        }

        // Clean up when composable is disposed
        onDispose {
            context.unregisterReceiver(locationReceiver)
        }
    }

    // Render the map with user location and other overlays
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
