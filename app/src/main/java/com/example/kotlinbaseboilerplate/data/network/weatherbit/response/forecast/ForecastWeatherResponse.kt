package com.example.kotlinbaseboilerplate.data.network.weatherbit.response.forecast


import com.google.gson.annotations.SerializedName

data class ForecastWeatherResponse(
    @SerializedName("city_name")
    val bitCityName: String,
    @SerializedName("country_code")
    val bitCountryCode: String,
    @SerializedName("data")
    val bitData: List<ForecastWeatherData>,
    @SerializedName("lat")
    val bitLat: String,
    @SerializedName("lon")
    val bitLon: String,
    @SerializedName("state_code")
    val bitStateCode: String,
    @SerializedName("timezone")
    val bitTimezone: String
)