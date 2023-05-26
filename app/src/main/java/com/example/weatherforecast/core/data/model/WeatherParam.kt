package com.example.weatherforecast.core.data.model

import android.os.Parcelable
import com.example.weatherforecast.core.domain.entity.DegreeUnit
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherParam(
    val cityName: String? = null,
    val zipCode: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val unit: DegreeUnit = DegreeUnit.Celsius
) : Parcelable {
    fun isAllNullOrBlank(): Boolean {
        return cityName.isNullOrBlank()
                && zipCode.isNullOrBlank()
                && (latitude == null || longitude == null)
    }
}
