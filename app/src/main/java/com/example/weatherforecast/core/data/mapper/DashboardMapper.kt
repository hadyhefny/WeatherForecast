package com.example.weatherforecast.core.data.mapper

import com.example.weatherforecast.core.data.model.WeatherMainResponse
import com.example.weatherforecast.core.domain.entity.WeatherDataEntity

fun WeatherMainResponse.toEntity() =
    WeatherDataEntity(
        name = name,
        time = dt,
        description = weather[0].description,
        temp = main.temp
    )