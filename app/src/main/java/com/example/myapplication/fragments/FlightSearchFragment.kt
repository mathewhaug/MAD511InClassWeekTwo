package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.BuildConfig
import com.example.myapplication.Network.NetworkResult
import com.example.myapplication.network.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

// ----- ViewModel -----

class PriceTrackerViewModel(
    private val repo: FlightsRepository
) : ViewModel() {

    private val _state =
        MutableStateFlow<NetworkResult<List<FlightOffer>>>(NetworkResult.Loading)
    val state: StateFlow<NetworkResult<List<FlightOffer>>> = _state

    fun search(origin: String, dest: String, date: String) = viewModelScope.launch {
        _state.value = NetworkResult.Loading
        _state.value = repo.searchWS(origin, dest, date)
    }
}

// Factory to inject repository
class PriceTrackerVMFactory(private val repo: FlightsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PriceTrackerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PriceTrackerViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown VM class")
    }
}

// ----- Repository -----

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
            NetworkResult.Error("Network failure: ${e.message}")
        } catch (e: HttpException) {
            NetworkResult.Error("HTTP ${e.code()}: ${e.message()}", e.code())
        } catch (e: Exception) {
            NetworkResult.Error("Unexpected error: ${e.message}")
        }
    }
}


class FlightSearchFragment : Fragment() {

    private val viewModel: PriceTrackerViewModel by lazy {
        val repo = FlightsRepository(
            authApi = RetrofitProvider.authApi,
            flightsApi = RetrofitProvider.flightsApi
        )
        ViewModelProvider(this, PriceTrackerVMFactory(repo))[PriceTrackerViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    FlightSearchScreen(
                        vm = viewModel,
                        onBack = { findNavController().navigateUp() }
                    )
                }
            }
        }
    }
}

// ----- Compose UI -----

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightSearchScreen(
    vm: PriceTrackerViewModel,
    onBack: () -> Unit
) {
    val state by vm.state.collectAsState()

    var origin by remember { mutableStateOf("YYZ") }
    var dest by remember { mutableStateOf("YWG") }
    var date by remember { mutableStateOf("2026-05-10") } // YYYY-MM-DD

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("Flight Search") },
                navigationIcon = {
                    TextButton(onClick = onBack) { Text("Back") }
                }
            )
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = origin,
                onValueChange = { origin = it.uppercase().take(3) },
                label = { Text("Origin (IATA)") },
                singleLine = true
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = dest,
                onValueChange = { dest = it.uppercase().take(3) },
                label = { Text("Destination (IATA)") },
                singleLine = true
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = date,
                onValueChange = { date = it },
                label = { Text("Departure (YYYY-MM-DD)") },
                singleLine = true
            )
            Spacer(Modifier.height(12.dp))
            Button(onClick = { vm.search(origin, dest, date) }) {
                Text("Search WestJet")
            }

            Spacer(Modifier.height(16.dp))

            when (val s = state) {
                is NetworkResult.Loading -> Text("Loading…")
                is NetworkResult.Error -> Text("Error: ${s.message}", color = Color.Red)
                is NetworkResult.Success -> {
                    val offers = s.data
                    if (offers.isEmpty()) {
                        Text("No offers found.")
                    } else {
                        offers.forEach { offer ->
                            OfferCard(offer)
                            Spacer(Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun OfferCard(offer: FlightOffer) {
    Card {
        Column(Modifier.padding(12.dp)) {
            Text(
                "Total: ${offer.price?.total ?: "--"} ${offer.price?.currency ?: ""}",
                fontWeight = FontWeight.Bold
            )
            val first = offer.itineraries.firstOrNull()
            first?.segments?.forEach { seg ->
                Text("${seg.carrierCode}${seg.number}: ${seg.departure.iataCode} → ${seg.arrival.iataCode}")
            }
        }
    }
}
