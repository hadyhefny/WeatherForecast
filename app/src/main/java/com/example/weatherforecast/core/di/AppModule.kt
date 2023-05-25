package com.example.weatherforecast.core.di

import android.content.Context
import android.content.SharedPreferences
import com.example.weatherforecast.core.data.repository.WeatherDataRepositoryImpl
import com.example.weatherforecast.core.data.source.local.LocalLocationDs
import com.example.weatherforecast.core.data.source.remote.WeatherService
import com.example.weatherforecast.core.domain.repository.WeatherDataRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("weather_preferences", Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Singleton
    @Provides
    fun provideLocalLocationDs(sharedPreferences: SharedPreferences, gson: Gson): LocalLocationDs {
        return LocalLocationDs(sharedPreferences, gson)
    }

    @Singleton
    @Provides
    fun provideWeatherDataRepository(
        weatherService: WeatherService,
        localLocationDs: LocalLocationDs
    ): WeatherDataRepository {
        return WeatherDataRepositoryImpl(weatherService, localLocationDs)
    }
}