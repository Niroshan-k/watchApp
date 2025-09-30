package com.example.testapp.model

data class WatchResponse(
    val watches: List<Watch> = emptyList(),
    val total: Int = 0
)