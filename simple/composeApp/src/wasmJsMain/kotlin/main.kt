import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import io.github.sample.App

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow("sample") {
        App()
    }
}
