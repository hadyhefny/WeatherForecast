package com.example.weatherforecast.modules.forecast.domain.entity

data class ForecastWeatherParam(
    val cityName: String? = null,
    val zipCode: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null
) {
    fun isAllNullOrBlank(): Boolean {
        return cityName.isNullOrBlank()
                && zipCode.isNullOrBlank()
                && (latitude == null || longitude == null)
    }
}
