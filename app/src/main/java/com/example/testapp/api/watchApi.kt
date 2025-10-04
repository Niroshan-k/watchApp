package com.example.testapp.network

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface WatchApi {
    @GET("make")
    suspend fun getMakesRaw(): Response<ResponseBody>
}