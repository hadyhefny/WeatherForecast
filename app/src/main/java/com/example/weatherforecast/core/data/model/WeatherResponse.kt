package com.example.weatherforecast.core.data.model

data class CurrentWeatherMainResponse(
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

data class ForecastWeatherMainResponse(
    val list: List<ForecastWeatherItem>,
    val city: City
)

data class ForecastWeatherItem(
    val dt: Long,
    val main: WeatherTemp,
    val weather: List<WeatherDescription>
)

data class City(val name: String)