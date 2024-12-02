package io.github.sample.data.model

import kotlinx.serialization.Serializable

@Serializable
data class DirectionsResponse(
    val routes: List<Route>,
)

@Serializable
data class Route(
    val overview_polyline: Polyline,
)

@Serializable
data class Polyline(
    val points: String,
)