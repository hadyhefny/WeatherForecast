package com.example.weatherforecast.modules.forecast.domain.entity

data class ForecastWeatherEntity(
    val name: String,
    val items: List<ForecastWeatherItemEntity>
)

data class ForecastWeatherItemEntity(
    val time: Long,
    val description: String,
    val temp: Double
)
