package com.salaheddin.weatherapp.models.responseModel

import com.google.gson.annotations.SerializedName
import com.salaheddin.weatherapp.utils.Utils

data class WeatherData(
    @SerializedName("dt")
    val time: Long,

    @SerializedName("main")
    val main: Main,

    @SerializedName("weather")
    val weather: ArrayList<Weather>,

    @SerializedName("wind")
    val wind: Wind,

    @SerializedName("name")
    val name: String,

    @SerializedName("message")
    val message: String,

    @SerializedName("cod")
    val code: Int
) {
    val date: String
        get() {
            return Utils.dateLongToDate(time * 1000)
        }
}