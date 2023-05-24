package com.example.weatherforecast.core.data.source.local

import android.content.SharedPreferences
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LocalLocationDs @Inject constructor(private val sharedPreferences: SharedPreferences) {
    fun getLastSavedLocation(): Single<String> {
        return Single.create { emitter ->
            val location: String? =
                sharedPreferences.getString("last_saved_location", DEFAULT_LOCATION)
            emitter.onSuccess(location ?: DEFAULT_LOCATION)
        }
    }

    fun saveLocation(location: String) {

    }

    companion object {
        private const val DEFAULT_LOCATION = "united states"
    }
}