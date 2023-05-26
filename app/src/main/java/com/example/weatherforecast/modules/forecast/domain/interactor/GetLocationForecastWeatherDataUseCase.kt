package com.example.weatherforecast.modules.forecast.domain.interactor

import com.example.weatherforecast.modules.forecast.domain.entity.ForecastWeatherEntity
import com.example.weatherforecast.core.domain.entity.WeatherParamEntity
import com.example.weatherforecast.modules.forecast.domain.repository.ForecastRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetLocationForecastWeatherDataUseCase @Inject constructor(
    private val forecastRepository: ForecastRepository
) {
    operator fun invoke(param: WeatherParamEntity): Single<ForecastWeatherEntity> {
        return forecastRepository.getLocationForecastWeatherData(param)
    }
}