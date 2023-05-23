package com.example.weatherforecast.core.data.mapper

import com.example.weatherforecast.core.data.model.CurrentWeatherMainResponse
import com.example.weatherforecast.core.domain.entity.WeatherDataEntity

fun CurrentWeatherMainResponse.toEntity() =
    WeatherDataEntity(
        name = name,
        time = dt,
        description = weather[0].description,
        temp = main.temp
    )