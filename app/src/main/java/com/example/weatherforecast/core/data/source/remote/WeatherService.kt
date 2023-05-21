package com.example.weatherforecast.core.data.source.remote

import com.example.weatherforecast.BuildConfig
import com.example.weatherforecast.core.data.model.WeatherMainResponse
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
        @Query("appid") string: String = BuildConfig.API_KEY
    ): Single<WeatherMainResponse>
}