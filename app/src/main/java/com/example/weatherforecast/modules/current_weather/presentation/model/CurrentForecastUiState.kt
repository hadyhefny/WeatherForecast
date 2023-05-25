package com.example.weatherforecast.modules.current_weather.presentation.model

data class CurrentWeatherUiState(
    val isLoading: Boolean = false,
    val cityName: String = "",
    val time: Long = 0L,
    val description: String = "",
    val temp: Double = 0.0
)