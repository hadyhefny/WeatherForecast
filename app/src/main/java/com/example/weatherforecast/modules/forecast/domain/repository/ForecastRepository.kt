package com.example.weatherforecast.modules.forecast.domain.repository

import com.example.weatherforecast.modules.forecast.domain.entity.ForecastWeatherEntity
import com.example.weatherforecast.core.domain.entity.WeatherParam
import io.reactivex.rxjava3.core.Single

interface ForecastRepository {
    fun getSavedLocationForecastWeatherData(): Single<ForecastWeatherEntity>
    fun getLocationForecastWeatherData(param: WeatherParam): Single<ForecastWeatherEntity>
}