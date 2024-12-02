package io.github.sample.data.api

import io.github.sample.data.model.DirectionsResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter


internal class KtorServices : KtorApi() {


    suspend fun getRoadPoints(origin: String, destination: String): DirectionsResponse {
        return client.get {
            pathUrl("directions/json")
            parameter("origin", origin)
            parameter("destination", destination)
            parameter("key", "")
        }.body()
    }

}