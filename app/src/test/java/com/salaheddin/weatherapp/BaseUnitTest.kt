package com.salaheddin.weatherapp

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import java.io.File

abstract class BaseUnitTest : KoinTest {

    private lateinit var mMockServerInstance: MockWebServer

    @Before
    open fun setUp() {
        startMockServer()
    }

    fun mockNetworkResponseWithFileContent(fileName: String, responseCode: Int) =
        mMockServerInstance.enqueue(
            MockResponse()
                .setResponseCode(responseCode)
                .setBody(getJson(fileName))
        )

    private fun getJson(path: String): String {
        val file = File(path)
        return String(file.readBytes())
    }

    private fun startMockServer() {
        mMockServerInstance = MockWebServer()
        mMockServerInstance.start()
    }

    fun getMockWebServerUrl() = mMockServerInstance.url("/").toString()

    private fun stopMockServer() {
        mMockServerInstance.shutdown()
    }

    @After
    open fun tearDown() {
        stopMockServer()
        stopKoin()
    }
}