package com.salaheddin.weatherapp.di

import com.google.gson.GsonBuilder
import com.salaheddin.weatherapp.network.ApiServices
import com.salaheddin.weatherapp.network.ResponseHandler
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Network module test configuration with mockserver url.
 */
fun configureNetworkModuleForTest(baseApi: String)
        = module{
    single {
        Retrofit.Builder()
            .baseUrl(baseApi)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }
    factory{ get<Retrofit>().create(ApiServices::class.java) }
    factory { ResponseHandler() }
}