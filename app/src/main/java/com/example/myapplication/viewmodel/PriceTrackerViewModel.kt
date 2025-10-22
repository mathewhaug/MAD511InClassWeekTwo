package com.example.myapplication.viewmodel

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Network.NetworkResult
import com.example.myapplication.network.FlightOffer
import com.example.myapplication.repository.FlightsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PriceTrackerViewModel(private val repo: FlightsRepository) : ViewModel() {
    private val _state = MutableStateFlow<NetworkResult<List<FlightOffer>>>(NetworkResult.Loading)
    val state: StateFlow<NetworkResult<List<FlightOffer>>> = _state

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun search(origin: String, dest: String, date: String) {
        viewModelScope.launch {
            _state.value = NetworkResult.Loading
            _state.value = repo.searchWS(origin, dest, date)
        }
    }
}
