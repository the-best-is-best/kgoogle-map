package io.github.kgooglemap

actual class KPlacesHelper actual constructor() {
    private val placesClient = GMSPlacesClient.sharedClient()

    actual fun fetchSuggestions(query: String, onResult: (List<String>) -> Unit) {
        if (query.length > 2) {
            val filter = GMSAutocompleteFilter()
            filter.type = GMSPlacesAutocompleteTypeFilterNoFilter // No filter applied

            placesClient.findAutocompletePredictionsWithQuery(
                query,
                filter,
                null
            ) { results, error ->
                if (error != null || results == null) {
                    onResult(emptyList())
                    return@findAutocompletePredictionsWithQuery
                }

                val suggestions = results.mapNotNull { it.attributedPrimaryText?.string }
                onResult(suggestions)
            }
        } else {
            onResult(emptyList())
        }
    }

}