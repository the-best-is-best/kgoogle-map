<h1 align="center">KGoogleMap</h1><br>
<div align="center">
<a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
<a href="https://android-arsenal.com/api?level=23" rel="nofollow">
    <img alt="API" src="https://img.shields.io/badge/API-23%2B-brightgreen.svg?style=flat" style="max-width: 100%;">
</a>
  <img src="https://img.shields.io/badge/Platform-Android-brightgreen.svg?logo=android" alt="Badge Android" />
  <img src="https://img.shields.io/badge/iOS-15%2B-blue.svg?logo=apple" alt="iOS 15+ Badge" />

<a href="https://github.com/the-best-is-best/"><img alt="Profile" src="https://img.shields.io/badge/github-%23181717.svg?&style=for-the-badge&logo=github&logoColor=white" height="20"/></a>
</div>

### KGoogleMap provides a unified API that allows developers to implement Google Maps functionalities in both Android and iOS applications with minimal platform-specific code

<hr>

[![Maven Central](https://img.shields.io/maven-central/v/io.github.the-best-is-best/kgoogle-map)](https://central.sonatype.com/artifact/io.github.the-best-is-best/kgoogle-map)

KGoogleMap is available on `mavenCentral()`.
![Demo](git_assets/video.gif)

## Install

```kotlin
implementation("io.github.the-best-is-best:kgoogle-map:1.0.1-rc4")
```

## Need add this in pod file if not exist run ` pod init `

```pod
  pod 'KGoogleMap', '0.1.2-rc.1'
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

### Second in android Main

```kotlin
AndroidKGoogleMap.initialization(this, "YOUR GOOGLE MAP KEY")
```

### in commonMain

```kotlin
   val mapController = remember {
    KMapController(
        initPosition = null
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

class GoogleMapViewModel : ViewModel() {
    private var debounceJob: Job? = null
    private val googlePlaces = KPlacesHelper()


    var selectedAddress1: PlaceDetails? = null
        private set

    var selectedAddress2: PlaceDetails? = null
        private set

    var directions: DirectionsResponse? = null
        private set

    // Function that will be called from the composable
    suspend fun onQueryChanged(query: String): List<AutocompleteSuggestion> {
        debounceJob?.cancel() // Cancel any ongoing job

        return if (query.isNotEmpty()) {
            delay(500) // Debounce delay
            fetchSuggestions(query)
        } else {
            emptyList()
        }
    }

    private suspend fun fetchSuggestions(query: String): List<AutocompleteSuggestion> {
        return withContext(Dispatchers.IO) {
            try {
                // Use suspendCancellableCoroutine to bridge the callback-based API with Kotlin coroutines
                suspendCancellableCoroutine { continuation ->
                    googlePlaces.fetchSuggestions(query) { suggestions ->
                        // Check if suggestions are not null and resume the coroutine with the result
                        continuation.resume(suggestions)
                    }
                }
            } catch (e: Exception) {
                // Handle any exceptions that occur during the fetching
                emptyList() // Return an empty list on error
            }
        }
    }

    suspend fun getPlaceDetails(placeId: String, searchId: Int) {
        withContext(Dispatchers.IO) {
            googlePlaces.fetchPlaceDetails(placeId, {
                if (searchId == 1) {
                    selectedAddress1 = it
                } else {
                    selectedAddress2 = it
                }
            })
        }
    }

    fun getRoad() {
        viewModelScope.launch {
            if (selectedAddress1 != null && selectedAddress2 != null) {
                val ktorServices = KtorServices()
                directions = ktorServices.getRoadPoints(
                    selectedAddress1!!.address!!,
                    selectedAddress2!!.address!!,
                )
            }
        }
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

## Google places

- Note no ui for search available make custom ui

### Functionality

```kotlin
expect class KPlacesHelper() {
    /**
     * Fetches autocomplete suggestions for a query string.
     *
     * @param query The search query string.
     * @param onResult A callback invoked with a list of suggestions or an empty list if no suggestions are found.
     */
    fun fetchSuggestions(query: String, onResult: (List<AutocompleteSuggestion>) -> Unit)

    /**
     * Fetches detailed information about a place given its place ID.
     *
     * @param placeId The ID of the place.
     * @param onResult A callback invoked with place details or `null` if the fetch operation fails.
     */
    fun fetchPlaceDetails(placeId: String, onResult: (PlaceDetails?) -> Unit)

}
```

### How use it

```kotlin
  KPlacesHelper().fetchSuggestions(query = "Ramses") {
                    print(it.size)
                    val sizeAddress = it.size
                    if (sizeAddress > 0) {
                        println(KPlacesHelper().fetchPlaceDetails(it[0].placeId, {
                            println("data address selected lat ${it?.latitude}")
                        }))
                    }
                }
```
