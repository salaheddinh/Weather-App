package com.salaheddin.weatherapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.salaheddin.weatherapp.di.configureTestAppComponent
import com.salaheddin.weatherapp.network.ApiServices
import com.salaheddin.weatherapp.network.ResponseHandler
import com.salaheddin.weatherapp.repositories.WeatherRepository
import kotlinx.coroutines.runBlocking
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
class WeatherRepositoryTest : BaseUnitTest() {

    private lateinit var mRepo: WeatherRepository
    private val mAPIService: ApiServices by inject()
    private val mResponseHandler: ResponseHandler by inject()

    private val cityName = "Dubai"
    private val lat = 25
    private val lon = 55
    private val weatherFilePath = "../app/src/test/assets/success_response_weather.json"
    private val forecastFilePath = "../app/src/test/assets/success_response_forecast.json"

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun start() {
        super.setUp()

        startKoin { modules(configureTestAppComponent(getMockWebServerUrl())) }
    }

    @Test
    fun test_getWeatherByName_retrieves_expected_data() = runBlocking {

        mockNetworkResponseWithFileContent(
            weatherFilePath,
            HttpURLConnection.HTTP_OK
        )

        mRepo = WeatherRepository(mAPIService, mResponseHandler)

        val dataReceived = mRepo.getWeatherByName(cityName)

        assertNotNull(dataReceived)
        assertEquals(dataReceived.data?.code, 200)
        assertEquals(dataReceived.data?.name, cityName)
        assertNotEquals(dataReceived.data?.weather?.size, 0)
        assertNotNull(dataReceived.data?.main)
    }

    @Test
    fun test_getWeather_retrieves_expected_data() = runBlocking {

        mockNetworkResponseWithFileContent(
            weatherFilePath,
            HttpURLConnection.HTTP_OK
        )

        mRepo = WeatherRepository(mAPIService, mResponseHandler)

        val dataReceived = mRepo.getWeather(lat,lon)

        assertNotNull(dataReceived)
        assertEquals(dataReceived.data?.code, 200)
        assertEquals(dataReceived.data?.name, cityName)
        assertNotEquals(dataReceived.data?.weather?.size, 0)
        assertNotNull(dataReceived.data?.main)
    }

    @Test
    fun test_getForecast5_retrieves_expected_data() = runBlocking {

        mockNetworkResponseWithFileContent(
            forecastFilePath,
            HttpURLConnection.HTTP_OK
        )

        mRepo = WeatherRepository(mAPIService, mResponseHandler)

        val dataReceived = mRepo.getForecast5(lat,lon)

        assertNotNull(dataReceived)
        assertEquals(dataReceived.code, 200)
        assertNotNull(dataReceived.data)
        assertNotEquals(dataReceived.data?.size, 0)
    }
}