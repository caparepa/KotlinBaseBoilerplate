package com.example.kotlinbaseboilerplate.data.network

import androidx.lifecycle.LiveData
import com.example.kotlinbaseboilerplate.data.network.response.CurrentWeatherResponse

interface WeatherNetworkDataSource {

    //Current downloaded weather data
    val downloadedCurrentWeather : LiveData<CurrentWeatherResponse>

    //This function doesn't return a CurrentWeatherResponse object, it will only update the
    //downloaded current weather LiveData, which it can be then observed in a Repository class
    //in a synchronous manner
    suspend fun fetchCurrentWeather(
        location: String,
        units: String
    )

}