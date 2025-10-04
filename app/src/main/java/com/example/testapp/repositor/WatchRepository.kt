package com.example.testapp.repositor

import com.example.testapp.model.Watch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import okhttp3.OkHttpClient
import okhttp3.Request

object WatchRepository {
    suspend fun fetchWatches(): List<Watch> = withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://raw.githubusercontent.com/Niroshan-k/fakeDatasetforTesting/main/watches.json")
            .build()
        val response = client.newCall(request).execute()
        val json = response.body?.string() ?: throw Exception("No response body")
        Json.decodeFromString(json)
    }
}