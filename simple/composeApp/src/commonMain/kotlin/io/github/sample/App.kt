package io.github.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.kgooglemap.KMapController
import io.github.kgooglemap.ui.CameraPosition
import io.github.kgooglemap.ui.KGoogleMapView
import io.github.kgooglemap.utils.LatLng
import io.github.sample.theme.AppTheme

@Composable
internal fun App() = AppTheme {
    val mapController = remember { KMapController(
        camera = CameraPosition(
            position = LatLng(30.08167 , 31.248462),
        )
    ) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Map view
            KGoogleMapView(controller = mapController)

            // Reset Location Icon
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Reset Location",
                modifier = Modifier
                    .size(140.dp)
                    .align(Alignment.BottomEnd) // Align it to bottom end for better visibility
                    .padding(bottom = 100.dp, end = 16.dp) // Adjust padding to avoid overlap
                    .clickable {
                        mapController.resetCamera()
                        println("Reset Location clicked")
                    },
                tint = Color.Black
            )

            // Semi-transparent bottom bar with full width
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(Color.White.copy(alpha = 0.7f))
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                // Fetch road button
                Button(
                    onClick = {
                        println("Fetch Road clicked")
                        mapController.fetchRoad(to = LatLng(30.08270, 31.258562))
                    },
                    modifier = Modifier.align(Alignment.Center),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black.copy(alpha = 0.7f)
                    ),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text("Fetch Road", color = Color.White, fontSize = 14.sp)
                }
            }
        }
    }
}
