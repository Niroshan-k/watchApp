package com.example.testapp.utils

import android.content.Context
import com.example.testapp.model.Watch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

object LocalWatchStorage {
    private const val FILE_NAME = "watches_offline.json"
    // /data/data/com.example.testapp/files/watches_offline.json

    fun saveWatches(context: Context, watches: List<Watch>) {
        val file = File(context.filesDir, FILE_NAME)
        val json = Json.encodeToString(watches)
        file.writeText(json)
    }

    fun loadWatches(context: Context): List<Watch>? {
        val file = File(context.filesDir, FILE_NAME)
        return if (file.exists()) {
            val json = file.readText()
            Json.decodeFromString(json)
        } else null
    }
}