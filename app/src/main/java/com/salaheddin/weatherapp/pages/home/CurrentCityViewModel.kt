package com.salaheddin.weatherapp.pages.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.salaheddin.weatherapp.models.DailyWeather
import com.salaheddin.weatherapp.models.Response
import com.salaheddin.weatherapp.models.responseModel.WeatherData
import com.salaheddin.weatherapp.repositories.WeatherRepository
import kotlinx.coroutines.Dispatchers

class CurrentCityViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {
    fun getForecast5(lan: Int, lon: Int): LiveData<Response<ArrayList<WeatherData>>> {
        return liveData(Dispatchers.IO) {
            emit(Response.loading(null))
            val response = weatherRepository.getForecast5(lan, lon)
            emit(response)
        }
    }

    fun getWeather(lan: Int, lon: Int): LiveData<Response<WeatherData>> {
        return liveData(Dispatchers.IO) {
            emit(Response.loading(null))
            val response = weatherRepository.getWeather(lan, lon)
            emit(response)
        }
    }

    fun getDailyDataGrouped(data: ArrayList<WeatherData>): ArrayList<DailyWeather> {
        val groupedData = data.groupBy { it.date }
        val dailyWeatherList = ArrayList<DailyWeather>()
        for ((date, list) in groupedData) {
            dailyWeatherList.add(DailyWeather(date, ArrayList(list)))
        }
        return dailyWeatherList
    }
}