package com.example.weatherforecast.modules.current_weather.data.repository

import com.example.weatherforecast.core.data.mapper.toEntity
import com.example.weatherforecast.core.data.source.local.LocalLocationDs
import com.example.weatherforecast.core.data.source.remote.WeatherService
import com.example.weatherforecast.core.domain.entity.WeatherDataEntity
import com.example.weatherforecast.core.domain.entity.WeatherParamEntity
import com.example.weatherforecast.modules.current_weather.domain.repository.CurrentWeatherRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class CurrentWeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService,
    private val localLocationDs: LocalLocationDs
) : CurrentWeatherRepository {
    override fun getCurrentLocationWeatherData(param: WeatherParamEntity): Single<WeatherDataEntity> {
        return weatherService.getCurrentWeatherData(
            cityName = param.cityName,
            zipCode = param.zipCode,
            lat = param.latitude,
            long = param.longitude,
            unit = param.unit.unit
        ).flatMap {
            localLocationDs.saveLocation(it.name).andThen(
                Single.defer {
                    Single.just(it)
                }
            )
        }.map {
            it.toEntity()
        }
    }

}