package com.example.kotlinbaseboilerplate.data.network.weatherbit.response.forecast


import com.google.gson.annotations.SerializedName

data class BitForecastWeatherResponse(
    @SerializedName("city_name")
    val bitCityName: String,
    @SerializedName("country_code")
    val bitCountryCode: String,
    @SerializedName("data")
    val bitData: List<ForecastDataItem>,
    @SerializedName("lat")
    val bitLat: String,
    @SerializedName("lon")
    val bitLon: String,
    @SerializedName("state_code")
    val bitStateCode: String,
    @SerializedName("timezone")
    val bitTimezone: String
)