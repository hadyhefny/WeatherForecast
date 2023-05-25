package com.example.weatherforecast.modules.current_weather.domain.repository

import com.example.weatherforecast.core.domain.entity.WeatherDataEntity
import com.example.weatherforecast.core.domain.entity.WeatherParam
import io.reactivex.rxjava3.core.Single

interface CurrentWeatherRepository {
    fun getCurrentLocationWeatherData(param: WeatherParam): Single<WeatherDataEntity>
}