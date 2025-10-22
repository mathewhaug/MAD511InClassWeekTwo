package com.example.myapplication.network

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

/*
 This API is a little advanced and needs to be OAuth2'ed and thats whats happening below
 */
interface AmadeusAuthApi {
    @FormUrlEncoded
    @POST("/v1/security/oauth2/token")
    suspend fun token(
        //getting the creds from build.gradle
        @Field("grant_type") grantType: String = "client_credentials",
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String
    ): TokenResponse
}

/** OAuth2 token response payload. */
data class TokenResponse(
    val access_token: String,
    val expires_in: Long,
    val token_type: String
)

/*
  FLIGHT OFFERS: search by origin, destination, date
  */
interface AmadeusFlightsApi {

    /**
     * GET /v2/shopping/flight-offers
     *
     * @param bearer   "Bearer <access_token>"
     * @param origin   IATA code (ex, "YYZ")
     * @param dest     IATA code (ex, "LAX")
     * @param date     YYYY-MM-DD
     * @param adults   number of adult pax
     * @param currency ISO currency (ex, "CAD")
     * @param airline  filter to airline (ex, "WS" for WestJet)
     * @param nonstop  optional nonstop filter
     * @param max      max offers to return
     */
    @GET("/v2/shopping/flight-offers")
    suspend fun search(
        @Header("Authorization") bearer: String,
        @Query("originLocationCode") origin: String,
        @Query("destinationLocationCode") dest: String,
        @Query("departureDate") date: String,
        @Query("adults") adults: Int = 1,
        @Query("currencyCode") currency: String = "CAD",
        @Query("includedAirlineCodes") airline: String = "WS",
        @Query("nonStop") nonstop: Boolean? = null,
        @Query("max") max: Int = 20
    ): Response<FlightOffersResponse>
}

/** Root response with flight offers. */
data class FlightOffersResponse(
    val data: List<FlightOffer> = emptyList()
)

/** One flight offer with price and itineraries. */
data class FlightOffer(
    val id: String,
    val price: Price?,
    val itineraries: List<Itin>
)

/** Pricing details for an offer. */
data class Price(
    val total: String,
    val currency: String
)

/** A complete itinerary (may include multiple segments). */
data class Itin(
    val segments: List<Segment>
)

/** A single flight leg between two airports. */
data class Segment(
    val departure: AirportTime,
    val arrival: AirportTime,
    val carrierCode: String, // ex, "WS"
    val number: String       // ex, "123"
)

/** Departure/arrival information. */
data class AirportTime(
    val iataCode: String, // ex, "YYZ"
    val at: String        // ISO 8601 date-time
)
