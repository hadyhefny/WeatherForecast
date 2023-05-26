package com.example.weatherforecast.modules.dashboard.presentation.model

data class DashboardUiState(
    val isLoading: Boolean = false,
    val cityName: String = "",
    val time: Long = 0L,
    val description: String = "",
    val temp: Double = 0.0,
    val isRetryButtonVisible: Boolean = true
)