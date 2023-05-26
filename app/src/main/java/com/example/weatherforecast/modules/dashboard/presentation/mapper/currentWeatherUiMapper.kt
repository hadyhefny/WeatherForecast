package com.example.weatherforecast.modules.dashboard.presentation.mapper

import com.example.weatherforecast.core.domain.entity.WeatherDataEntity
import com.example.weatherforecast.modules.dashboard.presentation.model.DashboardUiState

fun WeatherDataEntity.toUiState() = DashboardUiState(false, name, time, description, temp)