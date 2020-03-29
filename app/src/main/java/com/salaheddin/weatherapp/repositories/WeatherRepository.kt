package com.salaheddin.weatherapp.repositories

import com.salaheddin.weatherapp.models.Response
import com.salaheddin.weatherapp.models.responseModel.WeatherData
import com.salaheddin.weatherapp.network.ApiServices
import com.salaheddin.weatherapp.network.ResponseHandler
import com.salaheddin.weatherapp.utils.Constants

class WeatherRepository(
    private val apiService: ApiServices,
    private val responseHandler: ResponseHandler
) {

    suspend fun getForecast5(lan: Int, lon: Int): Response<ArrayList<WeatherData>> {
        try {
            val response = apiService.getForecast5(lan, lon, Constants.APP_ID, Constants.UNIT)

            return if (response.data != null) {
                try {
                    responseHandler.handleSuccess(response.data)

                } catch (e: Exception) {
                    responseHandler.handleException<ArrayList<WeatherData>>(response.code, e)
                }

            } else {
                responseHandler.handleException(response.code, Exception("Data not found"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return responseHandler.handleException(-1000, Exception("General Error"))
        }
    }

    suspend fun getWeather(lan: Int, lon: Int): Response<WeatherData> {
        try {
            val response = apiService.getWeather(lan, lon, Constants.APP_ID, Constants.UNIT)

            return if (response != null) {
                try {
                    responseHandler.handleSuccess(response)

                } catch (e: Exception) {
                    responseHandler.handleException<WeatherData>(-100, e)
                }

            } else {
                responseHandler.handleException(-100, Exception("Data not found"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return responseHandler.handleException<WeatherData>(-1000, Exception("General Error"))
        }
    }

    suspend fun getWeatherByName(cityName: String): Response<WeatherData> {
        try {
            val response = apiService.getWeatherByName(cityName, Constants.APP_ID, Constants.UNIT)

            return if (response != null) {
                try {
                    responseHandler.handleSuccess(response)

                } catch (e: Exception) {
                    responseHandler.handleException<WeatherData>(-100, e)
                }

            } else {
                responseHandler.handleException(-100, Exception("Data not found"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return responseHandler.handleException<WeatherData>(-1000, Exception("General Error"))
        }
    }
}