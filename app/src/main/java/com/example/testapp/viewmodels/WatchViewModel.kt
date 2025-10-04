package com.example.testapp.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.testapp.model.Watch
import com.example.testapp.repositor.WatchRepository
import com.example.testapp.utils.LocalWatchStorage
import com.example.testapp.utils.NetworkUtils

class WatchViewModel : ViewModel() {
    private val _watches = MutableStateFlow<List<Watch>>(emptyList())
    val watches: StateFlow<List<Watch>> = _watches

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    /** Loads watches depending on network status **/
    fun loadWatches(context: Context) {
        viewModelScope.launch {
            if (NetworkUtils.isNetworkAvailable(context)) {
                // Online: fetch from GitHub, save locally
                try {
                    val watches = withContext(Dispatchers.IO) { WatchRepository.fetchWatches() }
                    _watches.value = watches
                    withContext(Dispatchers.IO) { LocalWatchStorage.saveWatches(context, watches) }
                } catch (e: Exception) {
                    _error.value = e.message
                    Log.e("WatchAPI", "Error: ${e.message}", e)
                }
            } else {
                // Offline: load local JSON
                try {
                    val watches = withContext(Dispatchers.IO) { LocalWatchStorage.loadWatches(context) ?: emptyList() }
                    _watches.value = watches
                } catch (e: Exception) {
                    _error.value = e.message
                    Log.e("WatchAPI", "Error: ${e.message}", e)
                }
            }
        }
    }

    fun findWatchById(id: String?): Watch? {
        return _watches.value.find { it.id == id }
    }
}