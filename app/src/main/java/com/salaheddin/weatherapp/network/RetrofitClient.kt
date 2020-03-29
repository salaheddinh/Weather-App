package com.salaheddin.weatherapp.network

import com.salaheddin.weatherapp.utils.Constants

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(Constants.API_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideOkHttpClient(
    loggingInterceptor: HttpLoggingInterceptor,
    sslContext: SSLContext,
    x509TrustManager: X509TrustManager
): OkHttpClient {
    return OkHttpClient()
        .newBuilder()
        .sslSocketFactory(sslContext.socketFactory, x509TrustManager)
        .addInterceptor(loggingInterceptor)
        .connectTimeout(Constants.TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(Constants.TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(Constants.TIMEOUT, TimeUnit.SECONDS)
        .build()
}


fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    val logger = HttpLoggingInterceptor()
    logger.level = HttpLoggingInterceptor.Level.BODY
    return logger
}

fun provideSSLContext(x509TrustManager: X509TrustManager): SSLContext {

    try {

        val trustManagers = arrayOf<TrustManager>(x509TrustManager)

        val sslContext = SSLContext.getInstance(Constants.PROTOCOL_SSL)
        sslContext.init(null, trustManagers, java.security.SecureRandom())
        return sslContext
    } catch (e: Exception) {
        throw RuntimeException("Could not provide SSL context!", e)
    }

}

fun provideX509TrustManager(): X509TrustManager {

    return object : X509TrustManager {
        override fun getAcceptedIssuers(): Array<X509Certificate?> {

            return arrayOfNulls(0)
        }

        @Throws(CertificateException::class)
        override fun checkServerTrusted(
            chain: Array<X509Certificate>,
            authType: String
        ) {

        }

        @Throws(CertificateException::class)
        override fun checkClientTrusted(
            chain: Array<X509Certificate>,
            authType: String
        ) {

        }
    }

}

fun provideAppAPI(retrofit: Retrofit): ApiServices = retrofit.create(ApiServices::class.java)


