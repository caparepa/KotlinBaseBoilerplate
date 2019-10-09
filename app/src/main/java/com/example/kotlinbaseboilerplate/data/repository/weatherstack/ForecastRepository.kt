package com.example.kotlinbaseboilerplate.data.repository.weatherstack

import androidx.lifecycle.LiveData
import com.example.kotlinbaseboilerplate.data.db.weatherstack.entity.CurrentWeatherEntry
import com.example.kotlinbaseboilerplate.data.db.weatherstack.entity.WeatherLocation

interface ForecastRepository {

    /**
     * We create a suspended function to be accessed from a coroutine
     * It'll have no parameters since the units (C, F, S) will be fetched on the response
     * This function returns a LiveData of CurrentWeatherEntry
     */
    suspend fun getCurrentWeather(): LiveData<CurrentWeatherEntry>
    suspend fun getWeatherLocation(): LiveData<WeatherLocation>

}