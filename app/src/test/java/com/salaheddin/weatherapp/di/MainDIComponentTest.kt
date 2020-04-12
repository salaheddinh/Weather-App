package com.salaheddin.weatherapp.di

fun configureTestAppComponent(baseApi: String) = listOf(
    MockWebServerDIPTest,
    configureNetworkModuleForTest(baseApi),
    UserViewModelModule,
    RepositoryModule
)

