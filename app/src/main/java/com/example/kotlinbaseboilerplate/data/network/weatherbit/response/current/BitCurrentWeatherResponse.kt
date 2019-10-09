package com.example.kotlinbaseboilerplate.data.network.weatherbit.response.current


import com.google.gson.annotations.SerializedName

data class BitCurrentWeatherResponse(
    @SerializedName("count")
    val bitCount: Int,
    @SerializedName("data")
    val bitData: List<CurrentDataItem>
)