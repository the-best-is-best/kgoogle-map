package io.github.kgooglemap.ui

import androidx.compose.runtime.Composable
import io.github.kgooglemap.KMapController
import io.github.kgooglemap.utils.LatLng

@Composable
expect fun KGoogleMapView(
    controller: KMapController,
    onMapClick: ((LatLng) -> Unit)? = null,
    onMapLongClick: ((LatLng) -> Unit)? = null

)


