package com.example.testapp.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://watch-database1.p.rapidapi.com/"
    private const val RAPID_API_KEY = "ce69be383emshbc397476bb3f241p1bbd1ajsnf617017455f0"
    private const val RAPID_API_HOST = "watch-database1.p.rapidapi.com"

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original: Request = chain.request()
            val request = original.newBuilder()
                .addHeader("x-rapidapi-key", RAPID_API_KEY)
                .addHeader("x-rapidapi-host", RAPID_API_HOST)
                .build()
            chain.proceed(request)
        }
        .build()

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}