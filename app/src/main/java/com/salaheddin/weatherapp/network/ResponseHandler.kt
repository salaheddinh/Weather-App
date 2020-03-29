package com.salaheddin.weatherapp.network

import com.salaheddin.weatherapp.models.Response

enum class ErrorCodes(val code: Int) {
    SocketTimeOut(-1),
    NoInternetConnection(0)
}

open class ResponseHandler {
    fun <T : Any> handleSuccess(data: T): Response<T> {
        return Response.success(data)
    }

    fun <T : Any> handleException(code: Int, e: Exception): Response<T> {
        return Response.error(code, e.message!!)
    }

    private fun getErrorMessage(code: Int): String {
        return when (code) {
            ErrorCodes.SocketTimeOut.code -> "Timeout"
            ErrorCodes.NoInternetConnection.code -> "Timeout"
            401 -> "Unauthorised"
            404 -> "Not found"
            else -> "Something went wrong"
        }
    }
}
