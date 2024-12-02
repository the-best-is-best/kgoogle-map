package io.github.kgooglemap.services

import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import io.github.kgooglemap.AndroidKGoogleMap
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

internal class MyLocationServices {
    suspend fun getLocation(): Location? {
        val fusedLocationClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(AndroidKGoogleMap.getActivity())

        return suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    continuation.resume(location)
                } else {
                    continuation.resume(null) // No location available
                }
            }
            fusedLocationClient.lastLocation.addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
        }
    }
}
