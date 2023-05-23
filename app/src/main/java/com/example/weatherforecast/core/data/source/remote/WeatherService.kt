package com.example.weatherforecast.core.data.source.remote

import com.example.weatherforecast.BuildConfig
import com.example.weatherforecast.core.data.model.CurrentWeatherMainResponse
import com.example.weatherforecast.core.data.model.ForecastWeatherMainResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("weather")
    fun getCurrentWeatherData(
        @Query("q") cityName: String? = null,
        @Query("zip") zipCode: String? = null,
        @Query("lat") lat: Double? = null,
        @Query("lon") long: Double? = null,
        @Query("appid") appId: String = BuildConfig.API_KEY,
        @Query("units") unit: String = "metric"
    ): Single<CurrentWeatherMainResponse>

    @GET("forecast")
    fun getForecastWeatherData(
        @Query("q") cityName: String? = null,
        @Query("zip") zipCode: String? = null,
        @Query("lat") lat: Double? = null,
        @Query("lon") long: Double? = null,
        @Query("appid") appId: String = BuildConfig.API_KEY,
        @Query("units") unit: String = "metric"
    ): Single<ForecastWeatherMainResponse>
}