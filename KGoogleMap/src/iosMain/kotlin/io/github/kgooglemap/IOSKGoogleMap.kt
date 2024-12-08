package io.github.kgooglemap
import cocoapods.KGoogleMap.KGoogleMapInit
import io.github.tbib.klocation.IOSKLocationServices
import kotlinx.cinterop.ExperimentalForeignApi

class IOSKGoogleMap {
    companion object {
        @OptIn(ExperimentalForeignApi::class)
        fun init(key:String){
            IOSKLocationServices().requestPermission()
            KGoogleMapInit.provideAPIKeyWithKey(key)
        }
    }
}
