package com.example.kotlinbaseboilerplate.data.network.weatherbit.response.current


import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.current.CurrentWeatherData
import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    @SerializedName("count")
    val bitCount: Int,
    @SerializedName("data")
    val bitData: List<CurrentWeatherData>
)