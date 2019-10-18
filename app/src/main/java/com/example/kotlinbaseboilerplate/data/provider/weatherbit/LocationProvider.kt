package com.example.kotlinbaseboilerplate.data.provider.weatherbit

import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.current.CurrentWeatherData

interface LocationProvider {

    /**
     * This provider has functions related to the weather location in case the user decides to
     * move around or change locations
     */

    suspend fun hasLocationChanged(lastWeatherData: CurrentWeatherData): Boolean
    suspend fun getPreferredLocationString(): String

}