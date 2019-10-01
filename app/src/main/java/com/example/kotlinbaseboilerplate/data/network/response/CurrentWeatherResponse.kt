package com.example.kotlinbaseboilerplate.data.network.response

import com.example.kotlinbaseboilerplate.data.db.entity.CurrentWeatherEntry
import com.example.kotlinbaseboilerplate.data.db.entity.CurrentWeatherLocation
import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry,
    @SerializedName("location")
    val currentWeatherLocation: CurrentWeatherLocation,
    @SerializedName("request")
    val request: Request
)