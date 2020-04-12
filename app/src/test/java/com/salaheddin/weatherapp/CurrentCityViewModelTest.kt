package com.salaheddin.weatherapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.salaheddin.weatherapp.di.configureTestAppComponent
import com.salaheddin.weatherapp.pages.home.CurrentCityViewModel
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
class CurrentCityViewModelTest : BaseUnitTest() {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var mCurrentCityViewModel: CurrentCityViewModel
    private val weatherRepository: WeatherRepository by inject()

    private val weatherFilePath = "../app/src/test/assets/success_response_weather.json"
    private val forecastFilePath = "../app/src/test/assets/success_response_forecast.json"
    private val lat = 25
    private val lon = 55
    private val cityName = "Dubai"

    @Before
    fun start() {
        super.setUp()
        MockKAnnotations.init(this)
        startKoin { modules(configureTestAppComponent(getMockWebServerUrl())) }
    }

    @Test
    fun test_getWeather_retrieves_expected_data() {
        mCurrentCityViewModel = CurrentCityViewModel(weatherRepository)

        mockNetworkResponseWithFileContent(
            weatherFilePath,
            HttpURLConnection.HTTP_OK
        )

        mCurrentCityViewModel.getWeather(lat, lon).observeForever { observedValue ->
            assertNotNull(observedValue)
            assertEquals(observedValue.code, 200)
            assertEquals(observedValue.data?.name, cityName)
            assertNotEquals(observedValue.data?.weather?.size, 0)
            assertNotNull(observedValue.data?.main)
        }

    }

    @Test
    fun test_getForecast5_retrieves_expected_data() {
        mCurrentCityViewModel = CurrentCityViewModel(weatherRepository)

        mockNetworkResponseWithFileContent(
            forecastFilePath,
            HttpURLConnection.HTTP_OK
        )

        mCurrentCityViewModel.getForecast5(lat, lon).observeForever { observedValue ->
            assertNotNull(observedValue)
            assertEquals(observedValue.code, 200)
            assertNotNull(observedValue.data)
            assertNotEquals(observedValue.data?.size, 0)
        }
    }

    @Test
    fun test_getDailyDataGrouped_returns_expected_data() {
        mCurrentCityViewModel = CurrentCityViewModel(weatherRepository)

        mockNetworkResponseWithFileContent(
            forecastFilePath,
            HttpURLConnection.HTTP_OK
        )

        mCurrentCityViewModel.getForecast5(lat, lon).observeForever { observedValue ->
            assertNotNull(observedValue)
            assertEquals(observedValue.code, 200)
            assertNotNull(observedValue.data)
            assertNotEquals(observedValue.data?.size, 0)
        }

    }
}