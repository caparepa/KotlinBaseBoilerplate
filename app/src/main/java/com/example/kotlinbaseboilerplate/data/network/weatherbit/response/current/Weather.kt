package com.example.kotlinbaseboilerplate.data.network.weatherbit.response.current


import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("code")
    val bitCode: String,
    @SerializedName("description")
    val bitDescription: String,
    @SerializedName("icon")
    val bitIcon: String
)