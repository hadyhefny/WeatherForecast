package com.example.weatherforecast.core.data.source.local

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LocalLocationDs @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) {
    fun getLastSavedLocations(): Single<List<String>> {
        return Single.create { emitter ->
            val defaultLocationList = arrayListOf<String>()
            val defaultLocationString = gson.toJson(defaultLocationList)
            val savedLocationsString: String? =
                sharedPreferences.getString(SAVED_LOCATIONS, defaultLocationString)
            val myType = object : TypeToken<List<String>>() {}.type
            val savedLocationsList: ArrayList<String> = gson.fromJson(savedLocationsString, myType)
            emitter.onSuccess(savedLocationsList)
        }
    }

    fun saveLocation(location: String): Completable {
        return Completable.create { emitter ->
            val emptyList = arrayListOf<String>()
            val emptyListString = gson.toJson(emptyList)
            val savedLocationsString: String? =
                sharedPreferences.getString(SAVED_LOCATIONS, emptyListString)
            val myType = object : TypeToken<List<String>>() {}.type
            val savedLocationsList: ArrayList<String> = gson.fromJson(savedLocationsString, myType)
            if (savedLocationsList.contains(location)) {
                savedLocationsList.remove(location)
                savedLocationsList.add(location)
            } else if (savedLocationsList.size < 10) {
                savedLocationsList.add(location)
            } else {
                savedLocationsList.removeFirst()
                savedLocationsList.add(location)
            }
            sharedPreferences.edit().putString(SAVED_LOCATIONS, gson.toJson(savedLocationsList))
                .commit()
            emitter.onComplete()
        }
    }

    companion object {
        private const val SAVED_LOCATIONS = "saved locations"
    }
}