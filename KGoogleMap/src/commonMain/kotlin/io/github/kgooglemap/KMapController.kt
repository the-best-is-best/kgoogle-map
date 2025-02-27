package io.github.kgooglemap

import io.github.kgooglemap.utils.LatLng
import io.github.kgooglemap.utils.Markers

expect class KMapController(
     zoom: Float = 15f,
     initPosition: LatLng? = null,
     markers: List<Markers>? = null
) {
     fun resetCamera()
     fun renderRoad(points: String)
     fun addMarkers(markers: List<Markers>)
     fun clearMarkers()


     fun goToLocation(location: LatLng , zoom:Float = 15f)
     fun showLocationUser(show:Boolean)
     fun showRoad(show:Boolean)


}

