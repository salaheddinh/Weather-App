package com.salaheddin.weatherapp.models

import com.google.gson.annotations.SerializedName

data class Response<out T>(
    @SerializedName("cod")
    var code: Int,
    @SerializedName("list")
    val data: T? = null,
    @SerializedName("message")
    var message: String? = "Message is Null",
    var status: Status
) {
    companion object {

        fun <T> success(data: T?): Response<T> {
            return Response(
                200,
                data,
                null,
                Status.SUCCESS
            )
        }

        fun <T> error(code: Int, msg: String = ""): Response<T> {
            return Response(
                code,
                null,
                msg,
                Status.ERROR
            )
        }

        fun <T> loading(data: T? = null): Response<T> {
            return Response(
                0,
                data,
                null,
                Status.LOADING
            )
        }
    }
}
