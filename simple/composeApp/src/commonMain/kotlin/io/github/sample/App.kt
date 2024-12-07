package io.github.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import io.github.kgooglemap.KMapController
import io.github.kgooglemap.ui.KGoogleMapView
import io.github.kgooglemap.utils.LatLng
import io.github.kgooglemap.utils.Markers
import io.github.sample.theme.AppTheme
import io.github.sample.ui.TypeAhead
import kotlinx.coroutines.launch

@Composable
internal fun App() = AppTheme {
    val mapController = remember {
        KMapController(
            zoom = 15f

        )
    }

    var permissionLocation by remember { mutableStateOf(false) }

    val factory = rememberPermissionsControllerFactory()
    val controller = remember(factory) {
        factory.createPermissionsController()
    }

    BindEffect(controller)


    LaunchedEffect(permissionLocation) {
        controller.providePermission(Permission.LOCATION)
        permissionLocation = controller.isPermissionGranted(Permission.LOCATION)
//        if (permissionLocation) {
//            mapController.resetCamera()
//        }

    }

    val viewModel = GoogleMapViewModel()
    val scope = rememberCoroutineScope()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                TypeAhead(
                    itemsProvider = { query -> viewModel.onQueryChanged(query) },
                    itemToString = { it.fullText },
                    onItemSelected = {
                        scope.launch {
                            viewModel.getPlaceDetails(it.placeId, 1)
                        }
                    }
                )

                Spacer(Modifier.height(10.dp))

                TypeAhead(
                    itemsProvider = { query -> viewModel.onQueryChanged(query) },
                    itemToString = { it.fullText },
                    onItemSelected = {
                        scope.launch {
                            viewModel.getPlaceDetails(it.placeId, 2)
                        }
                    }
                )

                Spacer(Modifier.height(10.dp))

                ElevatedButton(onClick = { viewModel.getRoad() }) {
                    Text("Fetch Road")
                }

                Box(modifier = Modifier.fillMaxSize()) {
                    KGoogleMapView(controller = mapController)

                    Icon(
                        imageVector = Icons.Filled.Restore,
                        contentDescription = "Reset Location",
                        modifier = Modifier
                            .size(60.dp)
                            .align(Alignment.TopStart)
                            .padding(16.dp)
                            .clickable {
                                mapController.resetCamera()
                                println("Reset location")
                            },
                        tint = Color.Black
                    )

                    Icon(
                        imageVector = Icons.Filled.Place,
                        contentDescription = "Add Markers",
                        modifier = Modifier
                            .size(60.dp)
                            .align(Alignment.TopEnd)
                            .padding(16.dp)
                            .clickable {
                                mapController.addMarkers(
                                    listOf(
                                        Markers(
                                            LatLng(30.09167, 31.248662),
                                            "Marker 1",
                                            "Marker snippet 1"
                                        ),
                                        Markers(
                                            LatLng(30.10167, 31.250662),
                                            "Marker 2",
                                            "Marker snippet 2"
                                        )
                                    )
                                )
                                mapController.goToLocation(LatLng(30.10167, 31.250662))
                                println("Add markers")
                            },
                        tint = Color.Black
                    )

                    Icon(
                        imageVector = Icons.Filled.Remove,
                        contentDescription = "Clear Markers",
                        modifier = Modifier
                            .size(120.dp)
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                            .clickable {
                                mapController.clearMarkers()
                                println("Clear markers")
                            },
                        tint = Color.Black
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .background(Color.White.copy(alpha = 0.7f))
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            onClick = {
                                val start = viewModel.selectedAddress1
                                val end = viewModel.selectedAddress2
                                val directions = viewModel.directions

                                if (start != null && end != null && directions != null) {
                                    mapController.goToLocation(
                                        LatLng(
                                            start.latitude!!,
                                            start.longitude!!
                                        )
                                    )
                                    mapController.renderRoad(directions.routes.first().overview_polyline.points)
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                        ) {
                            Text("Render Road", color = Color.White, fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    // }
}