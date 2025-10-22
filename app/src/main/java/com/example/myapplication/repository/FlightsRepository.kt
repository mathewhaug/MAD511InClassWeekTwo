package com.example.myapplication.repository

import com.example.myapplication.BuildConfig
import com.example.myapplication.Network.NetworkResult // keep your actual package for NetworkResult
import com.example.myapplication.network.AmadeusAuthApi
import com.example.myapplication.network.AmadeusFlightsApi
import com.example.myapplication.network.FlightOffer
import retrofit2.HttpException
import java.io.IOException

class FlightsRepository(
    private val authApi: AmadeusAuthApi,
    private val flightsApi: AmadeusFlightsApi
) {
    private var cachedToken: String? = null
    private var tokenExpiryEpochSec: Long = 0

    private suspend fun bearer(): String {
        val now = System.currentTimeMillis() / 1000
        if (cachedToken == null || now >= tokenExpiryEpochSec - 60) {
            val t = authApi.token(
                clientId = BuildConfig.AMADEUS_CLIENT_ID,
                clientSecret = BuildConfig.AMADEUS_CLIENT_SECRET
            )
            //troubleshooitng 401
            android.util.Log.d("Amadeus", "token_type=${t.token_type}, exp=${t.expires_in}s")
            cachedToken = t.access_token
            tokenExpiryEpochSec = now + t.expires_in
        }
        return "Bearer $cachedToken"
    }

    suspend fun searchWS(
        origin: String,
        dest: String,
        date: String
    ): NetworkResult<List<FlightOffer>> {
        return try {
            val resp = flightsApi.search(
                bearer = bearer(),
                origin = origin.uppercase(),
                dest = dest.uppercase(),
                date = date
            )
            if (resp.isSuccessful) {
                NetworkResult.Success(resp.body()?.data.orEmpty())
            } else {
                NetworkResult.Error("API error", resp.code())
            }
        } catch (e: IOException) {
            NetworkResult.Error("Network failure: ${e.message ?: "IO error"}")
        } catch (e: HttpException) {
            NetworkResult.Error("HTTP ${e.code()}: ${e.message()}", e.code())
        } catch (e: Exception) {
            NetworkResult.Error("Unexpected error: ${e.message ?: "unknown"}")
        }
    }
}
