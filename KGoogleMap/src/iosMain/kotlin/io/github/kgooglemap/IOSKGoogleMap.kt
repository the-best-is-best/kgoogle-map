package io.github.kgooglemap
import cocoapods.KGoogleMap.KGoogleMapInit
import kotlinx.cinterop.ExperimentalForeignApi

class IOSKGoogleMap {
    companion object {
        @OptIn(ExperimentalForeignApi::class)
        fun init(key:String){
            KGoogleMapInit.provideAPIKeyWithKey(key)
        }
    }
}
