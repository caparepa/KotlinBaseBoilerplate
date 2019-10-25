package com.example.kotlinbaseboilerplate.data.network.weatherbit

import androidx.lifecycle.LiveData
import com.example.kotlinbaseboilerplate.data.network.weatherbit.response.current.CurrentWeatherResponse
import com.example.kotlinbaseboilerplate.data.network.weatherbit.response.forecast.ForecastWeatherResponse

interface WeatherNetworkDataSource {
    //Current downloaded weather data
    val downloadedCurrentWeather : LiveData<CurrentWeatherResponse>
    val downloadedFutureWeather: LiveData<ForecastWeatherResponse>

    //This function doesn't return a CurrentWeatherResponse object, it will only update the
    //downloaded current weather LiveData, which it can be then observed in a Repository class
    //in a synchronous manner
    suspend fun fetchCurrentWeather(
        location: String,
        language: String,
        units: String
    )

    suspend fun fetchFutureWeather(
        location: String,
        language: String,
        units: String
    )
}