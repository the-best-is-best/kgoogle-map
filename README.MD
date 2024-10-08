<h1 align="center">KGoogleMap</h1><br>
<div align="center">
<a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
<a href="https://android-arsenal.com/api?level=21" rel="nofollow">
    <img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat" style="max-width: 100%;">
</a>
  <img src="https://img.shields.io/badge/Platform-Android-brightgreen.svg?logo=android" alt="Badge Android" />
  <img src="https://img.shields.io/badge/iOS-15%2B-blue.svg?logo=apple" alt="iOS 15+ Badge" />

<a href="https://github.com/the-best-is-best/"><img alt="Profile" src="https://img.shields.io/badge/github-%23181717.svg?&style=for-the-badge&logo=github&logoColor=white" height="20"/></a>
</div>

### KGoogleMap provides a unified API that allows developers to implement Google Maps functionalities in both Android and iOS applications with minimal platform-specific code

<hr>

[![Maven Central](https://img.shields.io/maven-central/v/io.github.the-best-is-best/kgoogle-map)](https://central.sonatype.com/artifact/io.github.the-best-is-best/kgoogle-map)

KGoogleMap is available on `mavenCentral()`.

## Install

```kotlin
implementation("io.github.the-best-is-best:kgoogle-map:1.0.0-rc1")
```

## Need add this in pod file if not exist run ` pod init `

```pod
 pod 'KGoogleMap' , '0.1.0-rc.1'
```

## How to use it

### First in iosMain

```kotlin
fun MainViewController(): UIViewController
{
    IOSKGoogleMap.init("YOUR GOOGLE MAP KEY")
    return ComposeUIViewController { App() }
}

```

### in commonMain

```kotlin
 val mapController = remember {
        KMapController(
            camera = CameraPosition(
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

        }
    }   
```

### controller functionality

```kotlin
  fun resetCamera()
     fun renderRoad(points: String)
     fun addMarkers(markers: List<Markers>)
     fun clearMarkers()
     fun goToLocation(location: LatLng , zoom:Float = 15f)
     fun showLocationUser(show:Boolean)
     fun showRoad(show:Boolean)
```

### Example use controller

```kotlin
mapController.addMarkers([]) // sent new list
mapController.clearMarker() // remove all markers

mapController.goToLocation(location) // camera will move animated to new location

mapController.showLocationUser(show) // can show or hide current location user marker

mapController.renderRoad(points) // for render poly


 mapController.showRoad(show) // display poly or hide it
```
