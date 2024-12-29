package io.github.kgooglemap.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import cocoapods.KGoogleMap.KMapView
import io.github.kgooglemap.KMapController
import io.github.kgooglemap.utils.LatLng
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.pointed
import platform.CoreLocation.CLLocationCoordinate2D

@Composable
actual fun KGoogleMapView(
    controller: KMapController,
    onMapLoaded: (() -> Unit)?,
    onMapClick: ((LatLng) -> Unit)?,
    onMapLongClick: ((LatLng) -> Unit)?
) {
    var mapView: KMapView? = null

    // Use LaunchedEffect to ensure the initialization runs only once
    LaunchedEffect(Unit) {
        if (mapView != null) {
            controller.init(mapView!!)

        }
    }

    UIKitView(factory = {
        mapView = KMapView(
            controller.zoom,
            controller.markers,
            true,
            onMapLoaded
        )

        mapView!!.setClickListenerWithListener { coordinate ->
            memScoped {
                val pointer: CPointer<CLLocationCoordinate2D> = coordinate.getPointer(this)

                // Access latitude and longitude from the pointed struct
                val latitude = pointer.pointed.latitude
                val longitude = pointer.pointed.longitude
                onMapClick?.invoke(
                    LatLng(
                        latitude,  // Corrected: using latitude() and longitude()
                        longitude
                    )
                )
            }
        }

        mapView!!.setLongClickListenerWithListener { coordinate ->
            memScoped {
                val pointer: CPointer<CLLocationCoordinate2D> = coordinate.getPointer(this)

                // Access latitude and longitude from the pointed struct
                val latitude = pointer.pointed.latitude
                val longitude = pointer.pointed.longitude
                onMapLongClick?.invoke(
                    LatLng(
                        latitude,  // Corrected: using latitude() and longitude()
                        longitude
                    )
                )
            }
        }
        mapView!! // Return the map view
    },
        modifier = Modifier.fillMaxSize()
    )
}

