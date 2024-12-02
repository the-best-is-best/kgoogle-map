package io.github.sample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.kgooglemap.KPlacesHelper
import io.github.kgooglemap.utils.AutocompleteSuggestion
import io.github.kgooglemap.utils.PlaceDetails
import io.github.sample.data.api.KtorServices
import io.github.sample.data.model.DirectionsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume

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
