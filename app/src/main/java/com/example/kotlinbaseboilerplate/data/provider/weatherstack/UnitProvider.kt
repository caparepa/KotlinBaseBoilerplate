package com.example.kotlinbaseboilerplate.data.provider.weatherstack

import com.example.kotlinbaseboilerplate.internal.UnitSystem

interface UnitProvider {

    /**
     * This repository has a function to deal with the different unit systems used to show the
     * weather data (metric, imperial, scientific)
     */

    fun getUnitSystem() : UnitSystem
}