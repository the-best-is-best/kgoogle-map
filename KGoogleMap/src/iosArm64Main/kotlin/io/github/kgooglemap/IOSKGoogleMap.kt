package io.github.kgooglemap

import io.github.native.kgooglemap.KGoogleMapInit

actual object IOSKGoogleMap {
    actual fun init(key: String) {
        KGoogleMapInit.provideAPIKeyWithKey(key)
    }
}