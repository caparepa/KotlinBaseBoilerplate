package com.example.kotlinbaseboilerplate.ui.base

import androidx.lifecycle.ViewModel
import com.example.kotlinbaseboilerplate.data.provider.UnitProvider
import com.example.kotlinbaseboilerplate.data.repository.ForecastRepository
import com.example.kotlinbaseboilerplate.internal.UnitSystem
import com.example.kotlinbaseboilerplate.internal.lazyDeferred

abstract class WeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
): ViewModel() {

    //We need a way for the view to fetch the weather (a middleman)
    //The viewmodel will fetch the data from the repository and expose it to the view
    //so the view can fetch it (loose coupling)

    private val unitSystem = unitProvider.getUnitSystem()

    val isMetricUnit: Boolean
        get() = unitSystem == UnitSystem.METRIC

    //We also get the weather location via lazyDeferred in the viewmodel
    val weatherDescription by lazyDeferred{
        forecastRepository.getWeatherDescription()
    }

}