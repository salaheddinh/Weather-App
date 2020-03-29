package com.salaheddin.weatherapp.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.salaheddin.weatherapp.models.Response
import com.salaheddin.weatherapp.models.responseModel.WeatherData
import com.salaheddin.weatherapp.repositories.WeatherRepository
import kotlinx.coroutines.Dispatchers

class SearchViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {
    fun getCityWeather(cityName: String): LiveData<Response<WeatherData>> {
        return liveData(Dispatchers.IO) {
            emit(Response.loading(null))
            val response = weatherRepository.getWeatherByName(cityName)
            emit(response)
        }
    }
}