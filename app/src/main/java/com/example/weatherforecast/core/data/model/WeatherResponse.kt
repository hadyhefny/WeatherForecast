package com.example.weatherforecast.core.data.model

data class WeatherMainResponse(
    val weather: List<WeatherDescription>,
    val main: WeatherTemp,
    val dt: Long,
    val name: String
)

data class WeatherDescription(
    val description: String
)

data class WeatherTemp(
    val temp: Double
)