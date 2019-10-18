package com.example.kotlinbaseboilerplate.data.repository.weatherbit

import androidx.lifecycle.LiveData
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.current.CurrentWeatherData
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.current.WeatherDescription

interface BitForecastRepository {

    suspend fun getBitCurrentWeatherData() : LiveData<CurrentWeatherData>
    suspend fun getBitWeatherDescription() : LiveData<WeatherDescription>

}