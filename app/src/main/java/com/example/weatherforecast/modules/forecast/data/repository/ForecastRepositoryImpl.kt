package com.example.weatherforecast.modules.forecast.data.repository

import com.example.weatherforecast.core.data.source.local.LocalLocationDs
import com.example.weatherforecast.core.data.source.remote.WeatherService
import com.example.weatherforecast.modules.forecast.data.mapper.toEntity
import com.example.weatherforecast.modules.forecast.domain.entity.ForecastWeatherEntity
import com.example.weatherforecast.modules.forecast.domain.repository.ForecastRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService,
    private val localLocationDs: LocalLocationDs
) : ForecastRepository {
    override fun getSavedLocationForecastWeatherData(): Single<ForecastWeatherEntity> {
        return localLocationDs.getLastSavedLocation().flatMap { location ->
            weatherService.getForecastWeatherData(cityName = location).map {
                it.toEntity()
            }
        }
    }
}