import androidx.compose.ui.window.ComposeUIViewController
import io.github.kgooglemap.IOSKGoogleMap
import io.github.sample.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController
{
    IOSKGoogleMap.init("AIzaSyCF7KfFT1hqtVyfX4XQira2QPGZ9uxpclk")
    return ComposeUIViewController { App() }
}
