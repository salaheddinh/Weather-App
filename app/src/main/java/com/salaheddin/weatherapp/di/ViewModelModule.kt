package com.salaheddin.weatherapp.di

import com.salaheddin.weatherapp.pages.home.CurrentCityViewModel
import com.salaheddin.weatherapp.pages.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val UserViewModelModule = module {
    viewModel { CurrentCityViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}