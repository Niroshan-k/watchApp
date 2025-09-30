package com.example.testapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.testapp.network.RetrofitClient
import com.example.testapp.network.WatchApi
import com.example.testapp.model.Watch

class WatchViewModel : ViewModel() {
    private val api = RetrofitClient.instance.create(WatchApi::class.java)

    private val _watches = MutableStateFlow<List<Watch>>(emptyList())
    val watches: StateFlow<List<Watch>> = _watches

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchWatches() {
        viewModelScope.launch {
            try {
                val response = api.getMakes() // Adjust endpoint as needed
                _watches.value = response // or response.watches if using WatchResponse
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}