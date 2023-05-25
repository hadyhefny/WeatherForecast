package com.example.weatherforecast.modules.current_weather.presentation.mapper

import com.example.weatherforecast.core.domain.entity.WeatherDataEntity
import com.example.weatherforecast.modules.current_weather.presentation.model.CurrentWeatherUiState

fun WeatherDataEntity.toUiState() = CurrentWeatherUiState(false, name, time, description, temp)