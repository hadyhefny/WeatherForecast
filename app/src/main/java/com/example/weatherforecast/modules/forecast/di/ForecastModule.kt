package com.example.weatherforecast.modules.forecast.di

import com.example.weatherforecast.core.data.source.local.LocalLocationDs
import com.example.weatherforecast.core.data.source.remote.WeatherService
import com.example.weatherforecast.modules.forecast.data.repository.ForecastRepositoryImpl
import com.example.weatherforecast.modules.forecast.domain.repository.ForecastRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
class ForecastModule {
    @ViewModelScoped
    @Provides
    fun provideForecastWeatherRepository(
        weatherService: WeatherService,
        localLocationDs: LocalLocationDs
    ): ForecastRepository {
        return ForecastRepositoryImpl(weatherService, localLocationDs)
    }
}