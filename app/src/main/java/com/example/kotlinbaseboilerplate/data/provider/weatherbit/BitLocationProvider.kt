package com.example.kotlinbaseboilerplate.data.provider.weatherbit

import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.current.BitCurrentWeatherData

interface BitLocationProvider {

    /**
     * This provider has functions related to the weather location in case the user decides to
     * move around or change locations
     */

    suspend fun hasLocationChanged(lastWeatherData: BitCurrentWeatherData): Boolean
    suspend fun getPreferredLocationString(): String

}