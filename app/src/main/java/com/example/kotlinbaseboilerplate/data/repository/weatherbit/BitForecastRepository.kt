package com.example.kotlinbaseboilerplate.data.repository.weatherbit

import androidx.lifecycle.LiveData
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.current.BitCurrentWeatherData
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.current.BitWeatherDescription

interface BitForecastRepository {

    suspend fun getBitCurrentWeatherData() : LiveData<BitCurrentWeatherData>
    suspend fun getBitWeatherDescription() : LiveData<BitWeatherDescription>

}