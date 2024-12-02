package io.github.kgooglemap.services

import kotlinx.cinterop.memScoped
import kotlinx.cinterop.pointed
import kotlinx.coroutines.channels.Channel
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLLocationAccuracyNearestTenMeters
import platform.Foundation.NSError
import platform.darwin.NSObject

internal class LocationService : NSObject(), CLLocationManagerDelegateProtocol {
    private val locationManager = CLLocationManager()
    private var currentLocation: Pair<Double, Double>? = null
    private val locationUpdateChannel = Channel<Pair<Double, Double>>()

    init {
        locationManager.delegate = this
        locationManager.desiredAccuracy = kCLLocationAccuracyNearestTenMeters
        locationManager.requestWhenInUseAuthorization()
        locationManager.startUpdatingLocation()
    }

    override fun locationManager(
        manager: CLLocationManager,
        didUpdateLocations: List<*>
    ) {
        val location = didUpdateLocations.lastOrNull() as? CLLocation
        location?.let {
            memScoped {
                val coordinate = it.coordinate.ptr
                val latitude = coordinate.pointed.latitude
                val longitude = coordinate.pointed.longitude

                currentLocation = Pair(latitude, longitude)
                println("Current location: Latitude $latitude, Longitude $longitude")

                // Send the updated location to the channel
                locationUpdateChannel.trySend(currentLocation!!).isSuccess

                locationManager.stopUpdatingLocation() // Stop after the first update to conserve battery
            }
        }
    }

    override fun locationManager(
        manager: CLLocationManager,
        didFailWithError: NSError
    ) {
        println("Failed to retrieve location: ${didFailWithError.localizedDescription}")
        // Optionally, you can close the channel to indicate an error state
        locationUpdateChannel.close()
    }

    suspend fun getCurrentLocation(): Pair<Double, Double>? {
        // Wait for the location to be available from the channel
        return locationUpdateChannel.receiveCatching().getOrNull()
    }
}
