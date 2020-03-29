package com.salaheddin.weatherapp.network

import com.salaheddin.weatherapp.models.Response
import com.salaheddin.weatherapp.models.responseModel.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiServices {
    @GET("forecast")
    suspend fun getForecast5(
        @Query("lat") lat: Int, @Query("lon") lon: Int, @Query("appid") appId: String, @Query(
            "units"
        ) units: String
    ): Response<ArrayList<WeatherData>>

    @GET("weather")
    suspend fun getWeather(
        @Query("lat") lat: Int, @Query("lon") lon: Int, @Query("appid") appId: String, @Query(
            "units"
        ) units: String
    ): WeatherData

    @GET("weather")
    suspend fun getWeatherByName(@Query("q") name: String, @Query("appid") appId: String, @Query("units") units: String): WeatherData
}