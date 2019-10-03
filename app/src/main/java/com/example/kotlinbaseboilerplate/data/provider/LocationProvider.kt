package com.example.kotlinbaseboilerplate.data.provider

import com.example.kotlinbaseboilerplate.data.db.entity.WeatherLocation

interface LocationProvider {

    /**
     * This provider has functions related to the weather location in case the user decides to
     * move around or change locations
     */

    suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean
    suspend fun getPreferredLocationString(): String

}