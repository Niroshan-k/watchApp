package com.example.testapp.network

import retrofit2.http.GET
import retrofit2.http.Query

interface WatchApi {
    @GET("make")
    suspend fun getMakes(): List<String> // Adjust response type to match API
}