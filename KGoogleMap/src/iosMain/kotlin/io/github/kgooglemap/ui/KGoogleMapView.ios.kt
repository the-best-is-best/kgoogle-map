package io.github.kgooglemap.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import cocoapods.KGoogleMap.KMapView
import cocoapods.KGoogleMap.MarkerData
import io.github.kgooglemap.KMapController
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun KGoogleMapView(controller: KMapController) {

    var mapView: KMapView? = null

    // Use LaunchedEffect to ensure the initialization runs only once
    LaunchedEffect(Unit) {
        if (mapView != null) {
            controller.init(mapView!!)
        }
    }

    UIKitView(factory = {
        mapView = KMapView(controller.initCamera as objcnames.classes.GMSCameraPosition?, controller.markers, true)
        mapView!! // Return the map view
    },
        modifier = Modifier.fillMaxSize()
    )
}


