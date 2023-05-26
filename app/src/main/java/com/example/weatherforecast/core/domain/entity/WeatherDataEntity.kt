package com.example.weatherforecast.core.domain.entity

data class WeatherDataEntity(
    val name: String,
    val time: Long,
    val description: String,
    val temp: Double
)