package io.github.kgooglemap

import io.github.native.kgooglemap.KGoogleMapInit

class IOSKGoogleMap {
    companion object {
        fun init(key:String){
            KGoogleMapInit.provideAPIKeyWithKey(key)
        }
    }
}
