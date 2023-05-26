package com.example.weatherforecast.core.domain.interactor

import com.example.weatherforecast.core.domain.entity.WeatherDataEntity
import com.example.weatherforecast.core.domain.repository.WeatherDataRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetSavedLocationWeatherDataUseCase @Inject constructor(
    private val weatherDataRepository: WeatherDataRepository
) {
    operator fun invoke(): Single<WeatherDataEntity> {
        return weatherDataRepository.getSavedLocationWeatherData()
    }
}