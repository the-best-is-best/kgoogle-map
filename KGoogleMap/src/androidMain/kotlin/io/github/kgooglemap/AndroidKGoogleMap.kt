package io.github.kgooglemap

import android.app.Activity
import com.google.android.libraries.places.api.Places
import java.lang.ref.WeakReference

object AndroidKGoogleMap {
    private var activity: WeakReference<Activity?> = WeakReference(null)

    internal fun getActivity(): Activity {
        return activity.get()!!

    }

    fun initialization(activity: Activity, apiKey: String) {
        this.activity = WeakReference(activity)
        Places.initialize(activity, apiKey)

    }
}