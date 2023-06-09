package com.example.weatherforecast.core.data.repository

import com.example.weatherforecast.core.data.mapper.toEntity
import com.example.weatherforecast.core.data.source.local.LocalLocationDs
import com.example.weatherforecast.core.data.source.remote.WeatherService
import com.example.weatherforecast.core.domain.entity.WeatherDataEntity
import com.example.weatherforecast.core.domain.exception.NoLocationSavedException
import com.example.weatherforecast.core.domain.repository.WeatherDataRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class WeatherDataRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService,
    private val localLocationDs: LocalLocationDs
) :
    WeatherDataRepository {
    override fun getSavedLocationWeatherData(): Single<WeatherDataEntity> {
        return localLocationDs.getLastSavedLocations()
            .flatMap { location ->
                if (location.isEmpty()) {
                    Single.error(NoLocationSavedException())
                } else {
                    weatherService.getCurrentWeatherData(cityName = location.last()).map {
                        it.toEntity()
                    }
                }
            }
    }
}
