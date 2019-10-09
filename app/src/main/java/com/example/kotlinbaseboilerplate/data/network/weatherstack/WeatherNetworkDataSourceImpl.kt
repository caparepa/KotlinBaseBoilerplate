package com.example.kotlinbaseboilerplate.data.network.weatherstack

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlinbaseboilerplate.data.WeatherStackApiService
import com.example.kotlinbaseboilerplate.data.network.weatherstack.response.CurrentWeatherResponse
import com.example.kotlinbaseboilerplate.internal.NoConnectivityException

class WeatherNetworkDataSourceImpl(
    private val weatherStackApiService: WeatherStackApiService
) : WeatherNetworkDataSource {

    //Since LiveData in immutable, we create a backing field to update its value
    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()

    //Get the updated LiveData value
    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    //We fetch the current data from the api service and update the LiveData with the new value
    override suspend fun fetchCurrentWeather(location: String, units: String) {
        try {
            //We get the current weather from the api service
            val fetchCurrentWeather = weatherStackApiService
                .getCurrentWeather(location, units)
                .await()
            //We update the LiveData with the current weather
            _downloadedCurrentWeather.postValue(fetchCurrentWeather)
        } catch (e: NoConnectivityException) {
            //In case there is no internet, we catch the exception HERE
            Log.e("CONNECTIVITY", "No Internet Connection", e)
        }
    }
}