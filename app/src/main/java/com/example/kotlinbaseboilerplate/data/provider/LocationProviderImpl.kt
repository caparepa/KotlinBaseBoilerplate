package com.example.kotlinbaseboilerplate.data.provider

import android.content.Context
import com.example.kotlinbaseboilerplate.data.db.entity.WeatherLocation

class LocationProviderImpl(context: Context) : PreferenceProvider(context), LocationProvider {

    override suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        return true
    }

    override suspend fun getPreferredLocationString(): String {
        return "London"
    }
}