package com.example.kotlinbaseboilerplate.data.network.weatherbit.response.forecast


import com.google.gson.annotations.SerializedName

data class WeatherDescription(
    @SerializedName("code")
    val bitCode: Int,
    @SerializedName("description")
    val bitDescription: String,
    @SerializedName("icon")
    val bitIcon: String
)