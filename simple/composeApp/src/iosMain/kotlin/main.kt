import androidx.compose.ui.window.ComposeUIViewController
import io.github.kgooglemap.IOSKGoogleMap
import io.github.sample.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController
{
    IOSKGoogleMap.init("")
    return ComposeUIViewController { App() }
}
