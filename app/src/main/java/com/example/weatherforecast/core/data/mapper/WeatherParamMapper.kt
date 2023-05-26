package com.example.weatherforecast.core.data.mapper

import com.example.weatherforecast.core.data.model.WeatherParam
import com.example.weatherforecast.core.domain.entity.WeatherParamEntity

fun WeatherParam.toEntity() = WeatherParamEntity(cityName, zipCode, latitude, longitude, unit)