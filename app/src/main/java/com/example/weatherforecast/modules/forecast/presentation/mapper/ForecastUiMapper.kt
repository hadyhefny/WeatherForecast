package com.example.weatherforecast.modules.forecast.presentation.mapper

import com.example.weatherforecast.modules.forecast.domain.entity.ForecastWeatherEntity
import com.example.weatherforecast.modules.forecast.domain.entity.ForecastWeatherItemEntity
import com.example.weatherforecast.modules.forecast.presentation.model.ForecastItemUiState
import com.example.weatherforecast.modules.forecast.presentation.model.ForecastUiState

fun ForecastWeatherEntity.toUiState() =
    ForecastUiState(name = name, items = items.toUiState())

fun List<ForecastWeatherItemEntity>.toUiState() =
    map {
        ForecastItemUiState(
            time = it.time,
            description = it.description,
            temp = it.temp
        )
    }