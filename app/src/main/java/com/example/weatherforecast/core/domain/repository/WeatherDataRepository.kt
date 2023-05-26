package com.example.weatherforecast.core.domain.repository

import com.example.weatherforecast.core.domain.entity.WeatherDataEntity
import io.reactivex.rxjava3.core.Single

interface WeatherDataRepository {
    fun getSavedLocationWeatherData(): Single<WeatherDataEntity>
}