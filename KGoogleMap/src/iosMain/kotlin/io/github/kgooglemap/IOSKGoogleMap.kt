package io.github.kgooglemap
import cocoapods.KGoogleMap.KGoogleMapInit

class IOSKGoogleMap {
    companion object {
        fun init(key:String){
            KGoogleMapInit.provideAPIKeyWithKey(key)
        }
    }
}
