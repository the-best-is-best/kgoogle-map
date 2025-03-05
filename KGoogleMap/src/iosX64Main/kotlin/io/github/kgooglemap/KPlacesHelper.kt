package io.github.kgooglemap


import io.github.kgooglemap.utils.AutocompleteSuggestion
import io.github.kgooglemap.utils.PlaceDetails
import io.github.native.google_places.GMSAutocompleteFilter
import io.github.native.google_places.GMSAutocompletePrediction
import io.github.native.google_places.GMSAutocompleteSessionToken
import io.github.native.google_places.GMSPlace
import io.github.native.google_places.GMSPlaceFieldCoordinate
import io.github.native.google_places.GMSPlaceFieldFormattedAddress
import io.github.native.google_places.GMSPlaceFieldName
import io.github.native.google_places.GMSPlaceFieldPhoneNumber
import io.github.native.google_places.GMSPlaceFieldWebsite
import io.github.native.google_places.GMSPlacesAutocompleteTypeFilter
import io.github.native.google_places.GMSPlacesClient
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.CValue
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.pointed
import platform.CoreLocation.CLLocationCoordinate2D
import platform.Foundation.NSError

actual class KPlacesHelper actual constructor() {
    private val placesClient = GMSPlacesClient.sharedClient()

    actual fun fetchSuggestions(query: String, onResult: (List<AutocompleteSuggestion>) -> Unit) {
        if (query.length > 2) {
            val filter = GMSAutocompleteFilter()
            filter.type =
                GMSPlacesAutocompleteTypeFilter.kGMSPlacesAutocompleteTypeFilterNoFilter // No filter applied

            placesClient.findAutocompletePredictionsFromQuery(
                query,
                filter,
                GMSAutocompleteSessionToken.new()
            ) { results: List<*>?, error: NSError? ->
                if (error != null || results == null) {
                    onResult(emptyList())
                    return@findAutocompletePredictionsFromQuery
                }
                val suggestions = (results as List<GMSAutocompletePrediction>).map { prediction ->
                    AutocompleteSuggestion(
                        placeId = prediction.placeID,
                        primaryText = prediction.attributedPrimaryText.string,
                        fullText = prediction.attributedFullText.string
                    )
                }
                onResult(suggestions)
            }
        } else {
            onResult(emptyList())
        }
    }

    /**
     * Fetches detailed information about a place given its place ID.
     *
     * @param placeId The ID of the place.
     * @param onResult A callback invoked with place details or `null` if the fetch operation fails.
     */
    actual fun fetchPlaceDetails(
        placeId: String,
        onResult: (PlaceDetails?) -> Unit
    ) {
        // Define which fields you want to retrieve.
        val placeFields: ULong = GMSPlaceFieldName or
                GMSPlaceFieldFormattedAddress or
                GMSPlaceFieldCoordinate or
                GMSPlaceFieldPhoneNumber or
                GMSPlaceFieldWebsite

        placesClient.fetchPlaceFromPlaceID(
            placeId,
            placeFields,
            null
        ) { place: GMSPlace?, error: NSError? ->
            if (error != null || place == null) {
                onResult(null)
                return@fetchPlaceFromPlaceID
            }

            // Map the GMSPlace object to your custom PlaceDetails type.
            val data = getLatitudeLongitude(place)
            val placeDetails = PlaceDetails(
                id = place.placeID,
                name = place.name,
                address = place.formattedAddress,
                latitude = data.first,
                longitude = data.second,

                )

            onResult(placeDetails)
        }
    }

    private fun getLatitudeLongitude(place: GMSPlace): Pair<Double, Double> {
        // Access the coordinate as a CValue
        val coordinate: CValue<CLLocationCoordinate2D> = place.coordinate

        return memScoped {
            // Use getPointer with memScoped to get the pointer to the coordinate
            val pointer: CPointer<CLLocationCoordinate2D> = coordinate.getPointer(this)

            // Access latitude and longitude from the pointed struct
            val latitude = pointer.pointed.latitude
            val longitude = pointer.pointed.longitude
            Pair(latitude, longitude)
        }
    }


}


