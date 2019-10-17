package com.example.kotlinbaseboilerplate.data.network.weatherbit

import androidx.lifecycle.LiveData
import com.example.kotlinbaseboilerplate.data.network.weatherbit.response.current.BitCurrentWeatherResponse

interface BitWeatherNetworkDataSource {
    //Current downloaded weather data
    val downloadedCurrentWeather : LiveData<BitCurrentWeatherResponse>

    //This function doesn't return a CurrentWeatherResponse object, it will only update the
    //downloaded current weather LiveData, which it can be then observed in a Repository class
    //in a synchronous manner
    suspend fun fetchBitCurrentWeather(
        location: String,
        units: String
    )
}