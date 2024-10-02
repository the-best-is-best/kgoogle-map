package io.github.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ClearAll
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Pin
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.kgooglemap.KMapController
import io.github.kgooglemap.ui.CameraPosition
import io.github.kgooglemap.ui.KGoogleMapView
import io.github.kgooglemap.utils.LatLng
import io.github.kgooglemap.utils.Markers
import io.github.sample.theme.AppTheme

@Composable
internal fun App() = AppTheme {
    val mapController = remember {
        KMapController(
            camera = CameraPosition(
                //position = LatLng(30.08167, 31.248462),
                position = LatLng(30.0781597, 31.248458), zoom = 10f
            )
        )
    }

    var showUserLocation by rememberSaveable {mutableStateOf(true) }

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

            // Start action icon
            Icon(
                imageVector = Icons.Filled.LocationCity,
                contentDescription = "Start Action",
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.TopStart) // Align at the top start
                    .padding(16.dp) // Padding for better visibility
                    .clickable {
                        mapController.resetCamera()
                        showUserLocation =!showUserLocation
                        mapController.showLocationUser(showUserLocation)
                        println("Rest location")
                        // Perform start action
                    },
                tint = Color.Black
            )

            // End action icon
            Icon(
                imageVector = Icons.Filled.Pin,
                contentDescription = "End Action",
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.TopEnd) // Align at the top end
                    .padding(16.dp) // Padding for better visibility
                    .clickable {
                        mapController.addMarkers(
                            listOf(
                                Markers(LatLng(30.09167, 31.248662), title = "Marker 1", snippet = "Marker snippet 1"),
                                Markers(LatLng(30.10167, 31.250662), title = "Marker 2", snippet = "Marker snippet 2"),
                            )
                        )
                        mapController.goToLocation(LatLng(30.10167, 31.250662))
                        println("Add markers")
                        // Perform end action
                    },
                tint = Color.Black
            )

            // Reset Location Icon
            Icon(
                imageVector = Icons.Filled.ClearAll,
                contentDescription = "Reset Location",
                modifier = Modifier
                    .size(140.dp)
                    .align(Alignment.BottomEnd) // Align it to bottom end for better visibility
                    .padding(bottom = 100.dp, end = 16.dp) // Adjust padding to avoid overlap
                    .clickable {
                        mapController.clearMarkers()
                        println("clear markers")
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
                        mapController.goToLocation(LatLng(34.0515659, -118.2424561), zoom = 10f)
                        val rawString = """
    eir~FdezuOxyFzfFl`G|qZvtSzqo@d}EhlLtqIdjFhrTfvEdca@{_@p{\\~`\\f}MprT~qRbyPnvObbXd_TxaEdxYxjRxvUnz\\bDfeLbpLxv@pmLroD`fTxrYfkTti\\xp@baMj_MoG|xNrlNpiL~cKn`OloFroRbk@lmDn_FnxHnQnz^~Thlo@uVlcc@|rQrnM~lAvlP|sH~qUlkPbzRxvE`cF~rPn_K~b`@tbDpx\\toK|_g@ldD|s_@piIzwc@nzNtlNdrKxoTtkXpeXrsO`l_@brWbkjAptBxoa@npNfiRv_QtcbAxpBlwb@rt_@ry`@pcKjtPpuPj`H`mNpsFjzHfpN~hIdfbAp}JfxY`Fxo^vdUnhq@}k@~`bAkMvli@cClxIjmGfHblG~bRhtF|eI~jFffQzsLldRr`WdrKxtKxb_@zlNrxU|qUpfYdwNpzP|rRxsc@zgWfdYtdBrqR|~Iz~Qv`ApvRntK`jI`cZz}n@nsW`mcA`}WndvB~iCdiVpjJnlErfNplA`i@bcGojCpsl@yeEn|l@{hDtsu@tl@lxs@~~DditAjcJzrf@~qIr`l@bbJrme@~rI|zC~dBfrO|yEdne@qAj{y@Qrsj@diAdns@l}EpnYwmDjn|@ywAbso@~kB|au@`{Alj|@csLzxcBl^|cr@fyJvwd@rxNxnlAyuJhqWjtCbmy@lwNjwpA`cSdimApgBxeNwbCx~_@{dB|y~@cyC|tfBxU~di@inFpxi@_bLpad@~wEbcNkJdxTwkDdeX`aHxh\\pgKlhs@r]`c_@imGdjUeCvnYajFfnN`Gd}c@u_Unm\\itMldMinIpgBadIzhLskDdy\\}iFz|UuvPnfo@v_A`}n@nvR~da@pdS`qUfnDljY|qLrbRdwO|ig@jtKt}k@frM~qo@~~JpwS~bA~pSgeGbvQc{Bzib@yiWzkuAecD`dh@mjKnhf@qwBl_ZzpDn}Wg_CrfUonH~pVpA|bRapAthi@n|FbgPnd@|p[yi@vjVqzHrhd@cgG|zl@jyE~g^|oLjyYvvBprWlpDrtd@ahHbyhApiBteSxpNzkHjtw@|yDjaPrmTzu@tqZjv@f_TuyGjxL{yMlcFafGh{IxDthNpxC|nZvxBzyUnhEpieAdsN|zw@qmAblk@t}Bhb^gvA|ml@{vPjweAkkDhpj@mmFnyZ`eAnsLjxKbfDnhUnrTdaSjtH``m@f`b@xyBbyGrjJ_|DxyGcyD`~Gfe@drNluRd`GlaC}cBfgObcAhh]vM`q`@|oBvgv@
""".trimIndent()
                        mapController.renderRoad(rawString)
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

