package io.github.kgooglemap.ui

import android.annotation.SuppressLint
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
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
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
@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
actual fun KGoogleMapView(
    controller: KMapController
) {
    val context = LocalContext.current
    val myLocationServices = MyLocationServices()
    val permissionState = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)
    var currentUserLocation by remember { mutableStateOf<LatLng?>(null) }


    val cameraPositionState = rememberCameraPositionState {
        position = controller.cameraPosition
    }
    LaunchedEffect(Unit) {
        controller.init(cameraPositionState)
    }
    LaunchedEffect(permissionState.status) {
        if (permissionState.status.isGranted) {
            myLocationServices.getLocation(context) {
                currentUserLocation = it
            }

        } else if (permissionState.status.shouldShowRationale || !permissionState.status.isGranted) {
            permissionState.launchPermissionRequest()
        }

    }

    Box(modifier = Modifier.fillMaxSize()) {

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,

            ) {
            if (controller.showUserLocation) {
                currentUserLocation?.let { location ->
                    Circle(
                        center = location,
                        radius = 40.0, // 40 meters
                        fillColor = Color.Blue.copy(alpha = .5f), // Blue with transparency
                        strokeColor = Color.Blue,
                        strokeWidth = 2f
                    )
                }
            }
            if (controller.polylineOptions.points.isNotEmpty() && controller.showRoad) {
                Polyline(
                    points = controller.polylineOptions.points,
                    color = Color(controller.polylineOptions.color),
                    width = controller.polylineOptions.width
                )
            }

            controller.currentMarker?.forEach { marker ->
                AdvancedMarker(
                    state = MarkerState(
                        position = LatLng(
                            marker.latLng.latitude,
                            marker.latLng.longitude
                        )
                    ),
                    title = marker.title,
                    snippet = marker.snippet,

                    )
            }
        }


    }

}