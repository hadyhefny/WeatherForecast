package com.example.weatherforecast.core.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherParam(
    val cityName: String? = null,
    val zipCode: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null
) : Parcelable {
    fun isAllNullOrBlank(): Boolean {
        return cityName.isNullOrBlank()
                && zipCode.isNullOrBlank()
                && (latitude == null || longitude == null)
    }
}
