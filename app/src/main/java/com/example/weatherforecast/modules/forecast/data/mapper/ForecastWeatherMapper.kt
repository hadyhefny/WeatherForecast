package com.example.weatherforecast.modules.forecast.data.mapper

import com.example.weatherforecast.core.data.model.ForecastWeatherItem
import com.example.weatherforecast.core.data.model.ForecastWeatherMainResponse
import com.example.weatherforecast.modules.forecast.domain.entity.ForecastWeatherEntity
import com.example.weatherforecast.modules.forecast.domain.entity.ForecastWeatherItemEntity

fun ForecastWeatherMainResponse.toEntity() =
    ForecastWeatherEntity(name = city.name, items = list.toEntity())


fun List<ForecastWeatherItem>.toEntity(): List<ForecastWeatherItemEntity> {
    return map {
        ForecastWeatherItemEntity(
            time = it.dt,
            description = it.weather[0].description,
            temp = it.main.temp
        )
    }
}