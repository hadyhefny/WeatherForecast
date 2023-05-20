package com.example.weatherforecast.core.data.source.remote

import com.example.weatherforecast.core.data.model.WeatherResponseMain
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("weather")
    suspend fun getCurrentWeatherData(
        @Query("q") cityName: String?,
        @Query("zip") zipCode: String?,
        @Query("lat") lat: Double?,
        @Query("lon") long: Double?,
        @Query("appid") string: String
    ): WeatherResponseMain
}