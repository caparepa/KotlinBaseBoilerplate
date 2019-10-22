package com.example.kotlinbaseboilerplate.data.network.weatherbit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlinbaseboilerplate.data.WeatherBitApiService
import com.example.kotlinbaseboilerplate.data.network.weatherbit.response.current.CurrentWeatherResponse
import com.example.kotlinbaseboilerplate.data.network.weatherbit.response.forecast.ForecastWeatherResponse
import com.example.kotlinbaseboilerplate.internal.NoConnectivityException
import java.lang.Exception

class WeatherNetworkDataSourceImpl(
    private val weatherbitApiService: WeatherBitApiService
) : WeatherNetworkDataSource {

    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()
    private val _downloadedFutureWeather = MutableLiveData<ForecastWeatherResponse>()

    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override val downloadedFutureWeather: LiveData<ForecastWeatherResponse>
        get() = _downloadedFutureWeather

    override suspend fun fetchCurrentWeather(location: String, language: String, units: String) {
        try {

            val str = convertToLatLngString(location)

            val fetchBitCurrentWeather = if (str != null) {
                weatherbitApiService
                    .getCurrentWeatherByLatLon(str[0], str[1], language, units)
                    .await()
            } else {
                weatherbitApiService
                    .getCurrentWeather(location, language, units)
                    .await()
            }
            _downloadedCurrentWeather.postValue(fetchBitCurrentWeather)

        } catch (e: NoConnectivityException) {
            Log.e("CONNECTIVITY", "No Internet Connection", e)
        }
    }

    override suspend fun fetchFutureWeather(location: String, language: String, units: String) {
        try {

            val str = convertToLatLngString(location)

            val fetchBitFutureWeather = if (str != null) {
                weatherbitApiService
                    .getForecastWeatherByLatLong(str[0], str[1], language, units)
                    .await()
            } else {
                weatherbitApiService
                    .getForecastWeather(location, language, units)
                    .await()
            }
            _downloadedFutureWeather.postValue(fetchBitFutureWeather)

        } catch (e: NoConnectivityException) {
            Log.e("CONNECTIVITY", "No Internet Connection", e)
        }
    }

    private fun convertToLatLngString(location: String): List<String>? {
        val regex = "^(\\-?\\d+(\\.\\d+)?),\\s*(\\-?\\d+(\\.\\d+)?)\$".toRegex()
        try {
            if (location.matches(regex))
                return location.split(",")
        } catch (e: Exception) {
            Log.e("NUMBER_FORMAT", "Number format exception", e)
        }
        return null
    }
}