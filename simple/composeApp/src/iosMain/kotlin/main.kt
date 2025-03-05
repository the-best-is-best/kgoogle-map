import androidx.compose.ui.window.ComposeUIViewController
//import io.github.kgooglemap.IOSKGoogleMap
import io.github.sample.App
import io.github.tbib.klocation.IOSKLocationServices
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController
{
//    IOSKGoogleMap.init("AIzaSyCfDTGDYO4-EngFi8_89J3QalqSGnPeQCg")
    IOSKLocationServices().requestPermission()

    return ComposeUIViewController { App() }
}
