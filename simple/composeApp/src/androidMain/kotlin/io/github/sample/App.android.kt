package io.github.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.github.kgooglemap.AndroidKGoogleMap
import io.github.tbib.klocation.AccuracyPriority
import io.github.tbib.klocation.AndroidKLocationService
import io.github.tbib.klocation.KLocationService

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidKLocationService.initialization(this, AccuracyPriority.HIGH_ACCURACY)
        AndroidKGoogleMap.initialization(this, "")

        enableEdgeToEdge()
        setContent {
            KLocationService().ListenerToPermission()
            App()
        }
    }
}

@Preview
@Composable
fun AppPreview() { App() }
