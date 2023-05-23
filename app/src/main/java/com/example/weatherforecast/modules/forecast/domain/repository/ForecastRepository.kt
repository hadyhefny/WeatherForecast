package com.example.weatherforecast.modules.forecast.domain.repository

import com.example.weatherforecast.modules.forecast.domain.entity.ForecastWeatherEntity
import io.reactivex.rxjava3.core.Single

interface ForecastRepository {
    fun getSavedLocationForecastWeatherData(): Single<ForecastWeatherEntity>
}