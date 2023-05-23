package com.example.weatherforecast.modules.forecast.presentation.model

data class ForecastUiState(
    val isLoading: Boolean = false,
    val name: String = "",
    val items: List<ForecastItemUiState> = emptyList()
)

data class ForecastItemUiState(
    val time: Long = 0L,
    val description: String = "",
    val temp: Double = 0.0
)
