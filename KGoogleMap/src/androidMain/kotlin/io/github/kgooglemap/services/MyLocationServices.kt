package io.github.kgooglemap.services

import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng

internal class MyLocationServices {
    fun getLocation(context: Context, onLocationFetched: (LatLng?) -> Unit) {
        val fusedLocationClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(context)

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                val userLatLng = LatLng(location.latitude, location.longitude)
                println("latitude: ${location.latitude} - long: ${location.longitude}")
                onLocationFetched(userLatLng)
            } else {
                onLocationFetched(null) // No location available
            }
        }
    }
}