package com.salaheddin.weatherapp

import android.app.Application
import com.salaheddin.weatherapp.di.NetworkModule
import com.salaheddin.weatherapp.di.RepositoryModule
import com.salaheddin.weatherapp.di.UserViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class WeatherApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WeatherApplication)

            printLogger(level = Level.DEBUG)
            modules(
                listOf(
                    NetworkModule,
                    UserViewModelModule,
                    RepositoryModule
                )
            )
        }
    }
}