package com.example.weatherforecast.modules.current_weather.di

import com.example.weatherforecast.core.data.source.local.LocalLocationDs
import com.example.weatherforecast.core.data.source.remote.WeatherService
import com.example.weatherforecast.modules.current_weather.data.repository.CurrentWeatherRepositoryImpl
import com.example.weatherforecast.modules.current_weather.domain.repository.CurrentWeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
class CurrentWeatherModule {
    @ViewModelScoped
    @Provides
    fun provideCurrentWeatherRepository(
        weatherService: WeatherService,
        localLocationDs: LocalLocationDs
    ): CurrentWeatherRepository {
        return CurrentWeatherRepositoryImpl(weatherService, localLocationDs)
    }
}