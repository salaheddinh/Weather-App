package com.salaheddin.weatherapp.network

import com.salaheddin.weatherapp.models.Response

open class ResponseHandler {
    fun <T : Any> handleSuccess(data: T): Response<T> {
        return Response.success(data)
    }

    fun <T : Any> handleException(code: Int, e: Exception): Response<T> {
        return Response.error(code, e.message!!)
    }
}
