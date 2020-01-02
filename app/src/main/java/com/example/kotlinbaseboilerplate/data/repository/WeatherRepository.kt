package com.example.kotlinbaseboilerplate.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.kotlinbaseboilerplate.data.WeatherBitApiService
import com.example.kotlinbaseboilerplate.data.db.weatherbit.WeatherDatabase
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.current.CurrentWeatherData
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.current.WeatherDescription
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.forecast.ForecastWeatherData
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.forecast.ForecastWeatherLocationData
import com.example.kotlinbaseboilerplate.data.network.SafeApiRequest
import com.example.kotlinbaseboilerplate.data.provider.PreferenceProvider
import org.threeten.bp.ZonedDateTime

class WeatherRepository(
    private val weatherApi: WeatherBitApiService,
    private val weatherDb: WeatherDatabase,
    private val weatherPreference: PreferenceProvider
) : SafeApiRequest() {

    private val currentWeather = MutableLiveData<CurrentWeatherData>()
    private val forecastWeatherList = MutableLiveData<List<ForecastWeatherData>>()
    private val weatherByDate = MutableLiveData<ForecastWeatherData>()
    private val weatherDescription = MutableLiveData<WeatherDescription>()
    private val forecastLocation = MutableLiveData<ForecastWeatherLocationData>()


    private suspend fun fetchCurrentWeatherData() {

    }

    private suspend fun getFutureWeatherList() {

    }

    private suspend fun getFutureWeatherByDate() {

    }

    private suspend fun getWeatherDescription() {

    }

    private suspend fun getForecastLocation() {

    }

    private suspend fun initWeatherData() {

    }

    private suspend fun fetchCurrentWeather() {

    }

    private suspend fun fetchFutureWeather() {

    }

    private suspend fun persistCurrentWeather() {

    }

    private suspend fun persistFutureWeather() {

    }

    private fun isFetchNeeded(savedAt: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return savedAt.isBefore(thirtyMinutesAgo)
    }

}