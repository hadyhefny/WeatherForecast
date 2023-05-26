package com.example.weatherforecast.modules.current_weather.presentation.mapper

import android.app.Application
import android.text.format.DateUtils
import com.example.weatherforecast.R
import com.example.weatherforecast.core.domain.entity.DegreeUnit
import com.example.weatherforecast.core.domain.entity.WeatherDataEntity
import com.example.weatherforecast.modules.current_weather.presentation.model.CurrentWeatherUiState
import java.util.concurrent.TimeUnit

fun WeatherDataEntity.toUiState(context: Application, unit: DegreeUnit) = CurrentWeatherUiState(
    false,
    name,
    DateUtils.formatDateTime(
        context,
        TimeUnit.SECONDS.toMillis(time),
        DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_TIME,
    ),
    description,
    if (unit == DegreeUnit.Celsius) context.getString(
        R.string.temp_celsius,
        temp.toString()
    ) else context.getString(R.string.temp_fahrenheit, temp.toString())
)