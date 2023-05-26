package com.example.weatherforecast.modules.current_weather.presentation.model

data class CurrentWeatherUiState(
    val isLoading: Boolean = false,
    val cityName: String = "",
    val time: String = "",
    val description: String = "",
    val temp: String = "",
    val isRetryButtonVisible: Boolean = true
)