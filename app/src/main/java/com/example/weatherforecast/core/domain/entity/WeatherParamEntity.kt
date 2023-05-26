package com.example.weatherforecast.core.domain.entity

data class WeatherParamEntity(
    val cityName: String? = null,
    val zipCode: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val unit: DegreeUnit = DegreeUnit.Celsius
)
