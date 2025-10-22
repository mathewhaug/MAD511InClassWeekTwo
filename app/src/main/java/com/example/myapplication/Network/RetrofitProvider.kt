package com.example.myapplication.network

import com.example.myapplication.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * ------------------------------------------------------------
 * RetrofitProvider
 * ------------------------------------------------------------
 * Provides configured Retrofit instances for:
 *  - AmadeusAuthApi (OAuth2 token)
 *  - AmadeusFlightsApi (flight search)
 *
 * This object builds and reuses a single Retrofit instance
 * with logging and GSON serialization.
 * ------------------------------------------------------------
 */

object RetrofitProvider {

    // Logging interceptor to show request/response info in Logcat
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // OkHttp client with logging
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    // Retrofit instance using BuildConfig.AMADEUS_BASE
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.AMADEUS_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    // APIs available through this provider
    val authApi: AmadeusAuthApi by lazy {
        retrofit.create(AmadeusAuthApi::class.java)
    }

    val flightsApi: AmadeusFlightsApi by lazy {
        retrofit.create(AmadeusFlightsApi::class.java)
    }
}
