package com.salaheddin.weatherapp.di

import com.salaheddin.weatherapp.repositories.WeatherRepository
import org.koin.dsl.module


val RepositoryModule = module {
    single { WeatherRepository(get(), get()) }
}