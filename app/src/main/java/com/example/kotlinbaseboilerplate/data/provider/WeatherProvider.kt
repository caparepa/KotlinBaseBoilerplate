package com.example.kotlinbaseboilerplate.data.provider

interface WeatherProvider {
    fun saveLastSavedAt(savedAt: String)
    fun getLastSavedAt(): String?
}