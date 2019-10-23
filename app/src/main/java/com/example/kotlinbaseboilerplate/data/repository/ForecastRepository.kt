package com.example.kotlinbaseboilerplate.data.repository

import androidx.lifecycle.LiveData
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.current.CurrentWeatherData
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.current.WeatherDescription
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.forecast.ForecastWeatherData
import org.threeten.bp.LocalDate

interface ForecastRepository {

    suspend fun getBitCurrentWeatherData() : LiveData<CurrentWeatherData>

    suspend fun getFutureWeatherList(startDate: LocalDate): LiveData<out List<ForecastWeatherData>>

    suspend fun getBitWeatherDescription() : LiveData<WeatherDescription>

}