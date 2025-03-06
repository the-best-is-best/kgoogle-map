package io.github.kgooglemap

import io.github.native.kgooglemap.KGoogleMapInit

object IOSKGoogleMap {
    fun init(key: String) {
        KGoogleMapInit.provideAPIKeyWithKey(key)
    }
}