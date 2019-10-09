package com.example.kotlinbaseboilerplate.data.network.weatherstack.response

import com.example.kotlinbaseboilerplate.data.db.weatherstack.entity.CurrentWeatherEntry
import com.example.kotlinbaseboilerplate.data.db.weatherstack.entity.WeatherLocation
import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry,
    @SerializedName("location")
    val weatherLocation: WeatherLocation,
    @SerializedName("request")
    val request: Request
)