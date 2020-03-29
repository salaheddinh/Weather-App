package com.salaheddin.weatherapp.models.responseModel

import com.google.gson.annotations.SerializedName
import com.salaheddin.weatherapp.utils.Constants

data class Weather(
    @SerializedName("id")
    val id: Int,

    @SerializedName("main")
    val main: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("icon")
    val icon: String
) {
    val iconUrl: String
        get() {
            return Constants.Images_URL + icon + ".png"
        }
}