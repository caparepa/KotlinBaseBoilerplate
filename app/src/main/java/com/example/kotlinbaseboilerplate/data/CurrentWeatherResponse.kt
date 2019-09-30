package com.example.kotlinbaseboilerplate.data


import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    @SerializedName("currentWeatherEntry")
    val currentWeatherEntry: CurrentWeatherEntry,
    @SerializedName("location")
    val location: Location,
    @SerializedName("request")
    val request: Request
)