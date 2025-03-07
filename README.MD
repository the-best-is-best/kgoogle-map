# KGoogleMap

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

KGoogleMap is available on mavenCentral().

[![Thumbnail](https://img.youtube.com/vi/eF-OnkJoI0U/0.jpg)](https://www.youtube.com/watch?v=eF-OnkJoI0U)

## Install

```kotlin
implementation("io.github.the-best-is-best:kgoogle-map:1.1.1")
// Note after 1.1.1 will not need add klocation will can access it directly
implementation("io.github.the-best-is-best:klocation:1.0.6")
```

## iOS Setup using SPM (Swift Package Manager)

Add the following line in your `Package.swift` file:

```swift
dependencies: [
    .package(url: "https://github.com/the-best-is-best/KGoogleMap.git", from: "1.1.0")
]
```

### How to use it

#### First in `iosMain`

```kotlin
fun MainViewController(): UIViewController
{
    IOSKLocationServices().requestPermission()
    IOSKGoogleMap.init("YOUR GOOGLE MAP KEY")

    return ComposeUIViewController { App() }
}
```

#### Second in `androidMain`

```kotlin
AndroidKGoogleMap.initialization(this, "YOUR GOOGLE MAP KEY")
setContent {
    // add this
    KLocationService().ListenerToPermission()
    App()
}
```

#### In `commonMain`

```kotlin
@Composable
internal fun App() = AppTheme {
    val mapController = remember {
        KMapController(
            initPosition = LatLng(30.01306, 31.20885),
            zoom = 15f
        )
    }

    val viewModel by remember { mutableStateOf(GoogleMapViewModel()) }
    val scope = rememberCoroutineScope()

    if (viewModel.requestPermission) {
        KLocationService().EnableLocation()
        viewModel.requestPermission = false
    }

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
                KGoogleMapView(
                    controller = mapController,
                    onMapLoaded ={
                        println("map loaded")
                    },
                    onMapClick = {
                        println("click loc :${it}")
                    },
                    onMapLongClick = {
                        println("long click loc :${it}")
                    }
                )
            }
        }
    }
}

class GoogleMapViewModel : ViewModel() {
    private val googlePlaces = KPlacesHelper()
    var selectedAddress1: PlaceDetails? = null
        private set
    var selectedAddress2: PlaceDetails? = null
        private set
    var directions: DirectionsResponse? = null
        private set
    private val locationService = KLocationService()
    var isGPSEnabled by mutableStateOf(true)
        private set
    var requestPermission by mutableStateOf(false)

    init {
        viewModelScope.launch {
            locationService.gpsStateFlow().collect { gps ->
                isGPSEnabled = gps
            }
        }
    }

    suspend fun onQueryChanged(query: String): List<AutocompleteSuggestion> {
        return if (query.isNotEmpty()) {
            delay(500)
            fetchSuggestions(query)
        } else {
            emptyList()
        }
    }

    fun enableGPSAndLocation() {
        requestPermission = true
    }

    private suspend fun fetchSuggestions(query: String): List<AutocompleteSuggestion> {
        return withContext(Dispatchers.IO) {
            try {
                suspendCancellableCoroutine { continuation ->
                    googlePlaces.fetchSuggestions(query) { suggestions ->
                        continuation.resume(suggestions)
                    }
                }
            } catch (e: Exception) {
                emptyList()
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
