package com.example.catsappchallenge.data.database

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    private val jsonFormat = Json {
        ignoreUnknownKeys = true
    }

    @TypeConverter
    fun fromStringListToJson(value: List<String>?): String? {
        return value?.let {
            jsonFormat.encodeToString(it)
        }
    }

    @TypeConverter
    fun fromJsonStringToList(value: String?): List<String>? {
        return value?.let {
            jsonFormat.decodeFromString(it)
        }
    }
}