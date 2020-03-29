package com.salaheddin.weatherapp.models

import com.salaheddin.weatherapp.models.responseModel.WeatherData

data class DailyWeather(val date: String, val weatherList: ArrayList<WeatherData>)