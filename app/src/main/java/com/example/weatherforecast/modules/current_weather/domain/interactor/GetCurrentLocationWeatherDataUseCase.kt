package com.example.weatherforecast.modules.current_weather.domain.interactor

import com.example.weatherforecast.core.domain.entity.WeatherDataEntity
import com.example.weatherforecast.core.domain.entity.WeatherParam
import com.example.weatherforecast.modules.current_weather.domain.repository.CurrentWeatherRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetCurrentLocationWeatherDataUseCase @Inject constructor(
    private val repository: CurrentWeatherRepository
) {
    operator fun invoke(param: WeatherParam): Single<WeatherDataEntity> {
        return repository.getCurrentLocationWeatherData(param)
    }
}