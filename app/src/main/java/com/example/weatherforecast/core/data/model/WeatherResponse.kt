package com.example.weatherforecast.core.data.model

data class WeatherResponseMain(
    val main: WeatherResponse
)

data class WeatherResponse(
    val temp: String
)