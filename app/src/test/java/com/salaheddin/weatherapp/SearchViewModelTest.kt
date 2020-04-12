package com.salaheddin.weatherapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.salaheddin.weatherapp.di.configureTestAppComponent
import com.salaheddin.weatherapp.pages.search.SearchViewModel
import com.salaheddin.weatherapp.repositories.WeatherRepository
import io.mockk.MockKAnnotations
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin
import org.koin.test.inject
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class SearchViewModelTest : BaseUnitTest() {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var mSearchViewModel: SearchViewModel
    private val weatherRepository: WeatherRepository by inject()

    private val weatherFilePath = "../app/src/test/assets/success_response_weather.json"
    private val cityName = "Dubai"

    @Before
    fun start() {
        super.setUp()
        MockKAnnotations.init(this)
        startKoin { modules(configureTestAppComponent(getMockWebServerUrl())) }
    }

    @Test
    fun test_getCityWeather_retrieves_expected_data() {
        mSearchViewModel = SearchViewModel(weatherRepository)

        mockNetworkResponseWithFileContent(
            weatherFilePath,
            HttpURLConnection.HTTP_OK
        )

        mSearchViewModel.getCityWeather(cityName).observeForever { observedValue ->
            assertNotNull(observedValue)
            assertEquals(observedValue.code, 200)
            assertEquals(observedValue.data?.name, cityName)
            assertNotEquals(observedValue.data?.weather?.size, 0)
            assertNotNull(observedValue.data?.main)
        }

    }
}