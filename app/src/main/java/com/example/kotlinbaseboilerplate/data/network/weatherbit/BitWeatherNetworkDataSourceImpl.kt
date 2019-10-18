package com.example.kotlinbaseboilerplate.data.network.weatherbit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlinbaseboilerplate.data.WeatherBitApiService
import com.example.kotlinbaseboilerplate.data.network.weatherbit.response.current.BitCurrentWeatherResponse
import com.example.kotlinbaseboilerplate.internal.NoConnectivityException
import java.lang.Exception
import java.lang.NumberFormatException

class BitWeatherNetworkDataSourceImpl(
    private val weatherBitApiService: WeatherBitApiService
) : BitWeatherNetworkDataSource {

    private val _downloadedBitCurrentWeather = MutableLiveData<BitCurrentWeatherResponse>()

    override val downloadedBitCurrentWeather: LiveData<BitCurrentWeatherResponse>
        get() = _downloadedBitCurrentWeather

    override suspend fun fetchBitCurrentWeather(location: String, language: String, units: String) {
        try {

            val str = convertToLatLngString(location)

            val fetchBitCurrentWeather = if (str != null) {
                weatherBitApiService
                    .getBitCurrentWeatherByLatLon(str[0], str[1], language, units)
                    .await()
            } else {
                weatherBitApiService
                    .getBitCurrentWeather(location, language, units)
                    .await()
            }
            _downloadedBitCurrentWeather.postValue(fetchBitCurrentWeather)

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