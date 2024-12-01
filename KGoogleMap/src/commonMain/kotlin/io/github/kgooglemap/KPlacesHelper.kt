package io.github.kgooglemap

import io.github.kgooglemap.utils.AutocompleteSuggestion
import io.github.kgooglemap.utils.PlaceDetails

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

