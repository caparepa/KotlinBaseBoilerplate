package com.example.kotlinbaseboilerplate.data.provider

import com.example.kotlinbaseboilerplate.data.db.entity.WeatherLocation

class LocationProviderImpl : LocationProvider {

    /**
     * Meanwhile, we'll return dummy values
     */

    override suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        return true
    }

    override suspend fun getPreferredLocationString(): String {
        return "London"
    }
}