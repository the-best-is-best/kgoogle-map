package io.github.kgooglemap

import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import io.github.kgooglemap.utils.AutocompleteSuggestion
import io.github.kgooglemap.utils.PlaceDetails

actual class KPlacesHelper actual constructor() {
    private val context = AndroidKGoogleMap.getActivity()
    private val placesClient = Places.createClient(context)
    private val token = AutocompleteSessionToken.newInstance()

    actual fun fetchSuggestions(query: String, onResult: (List<AutocompleteSuggestion>) -> Unit) {
        if (query.length > 2) {
            val request = FindAutocompletePredictionsRequest.builder()
                .setQuery(query)
                .setSessionToken(token)
                .build()

            placesClient.findAutocompletePredictions(request)
                .addOnSuccessListener { response ->
                    val suggestions = response.autocompletePredictions.map {
                        AutocompleteSuggestion(
                            placeId = it.placeId,
                            primaryText = it.getPrimaryText(null).toString(),
                            fullText = it.getFullText(null).toString()
                        )
                    }
                    onResult(suggestions)
                }
                .addOnFailureListener {
                    onResult(emptyList())
                }
        } else {
            onResult(emptyList())
        }
    }

    actual fun fetchPlaceDetails(placeId: String, onResult: (PlaceDetails?) -> Unit) {
        val placeFields = listOf(
            com.google.android.libraries.places.api.model.Place.Field.ID,
            com.google.android.libraries.places.api.model.Place.Field.NAME,
            com.google.android.libraries.places.api.model.Place.Field.LAT_LNG,
            com.google.android.libraries.places.api.model.Place.Field.ADDRESS
        )

        val request = FetchPlaceRequest.builder(placeId, placeFields).build()

        placesClient.fetchPlace(request)
            .addOnSuccessListener { response ->
                val place = response.place
                onResult(
                    PlaceDetails(
                        id = place.id,
                        name = place.name,
                        address = place.address,
                        latitude = place.latLng?.latitude,
                        longitude = place.latLng?.longitude
                    )
                )
            }
            .addOnFailureListener {
                onResult(null)
            }
    }
}