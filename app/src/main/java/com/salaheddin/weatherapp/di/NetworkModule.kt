package com.salaheddin.weatherapp.di

import com.salaheddin.weatherapp.network.*
import org.koin.dsl.module


val NetworkModule = module {

    factory {
        provideOkHttpClient(
            get(),
            provideSSLContext(provideX509TrustManager()),
            provideX509TrustManager()
        )
    }
    factory { provideLoggingInterceptor() }

    single { provideRetrofit(get()) }

    factory { provideAppAPI(get()) }
    factory { ResponseHandler() }
}