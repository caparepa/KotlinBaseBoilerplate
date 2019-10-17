package com.example.kotlinbaseboilerplate.data.network.weatherbit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlinbaseboilerplate.data.WeatherBitApiService
import com.example.kotlinbaseboilerplate.data.network.weatherbit.response.current.BitCurrentWeatherResponse
import com.example.kotlinbaseboilerplate.internal.NoConnectivityException

class BitWeatherNetworkDataSourceImpl(
    private val weatherBitApiService: WeatherBitApiService
) : BitWeatherNetworkDataSource {

    private val _downloadedBitCurrentWeather = MutableLiveData<BitCurrentWeatherResponse>()

    override val downloadedBitCurrentWeather: LiveData<BitCurrentWeatherResponse>
        get() = _downloadedBitCurrentWeather

    override suspend fun fetchBitCurrentWeather(location: String, language: String, units: String) {
        try {
            val fetchBitCurrentWeather = weatherBitApiService
                .getBitCurrentWeather(location, language, units)
                .await()
            _downloadedBitCurrentWeather.postValue(fetchBitCurrentWeather)
        }catch (e: NoConnectivityException){
            Log.e("CONNECTIVITY", "No Internet Connection", e)
        }
    }
}