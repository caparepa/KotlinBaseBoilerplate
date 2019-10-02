package com.example.kotlinbaseboilerplate.data.repository

import androidx.lifecycle.LiveData
import com.example.kotlinbaseboilerplate.data.db.entity.CurrentWeatherEntry

interface ForecastRepository {

    /**
     * We create a suspended function to be accessed from a coroutine
     * It'll have no parameters since the units (C, F, S) will be fetched on the response
     * This function returns a LiveData of CurrentWeatherEntry
     */
    suspend fun getCurrentWeather(): LiveData<CurrentWeatherEntry>

}