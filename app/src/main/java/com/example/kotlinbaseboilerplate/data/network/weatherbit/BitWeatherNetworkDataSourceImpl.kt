package com.example.kotlinbaseboilerplate.data.network.weatherbit

import androidx.lifecycle.LiveData
import com.example.kotlinbaseboilerplate.data.network.weatherbit.response.current.BitCurrentWeatherResponse

class BitWeatherNetworkDataSourceImpl() : BitWeatherNetworkDataSource {
    override val downloadedCurrentWeather: LiveData<BitCurrentWeatherResponse>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override suspend fun fetchBitCurrentWeather(location: String, units: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}