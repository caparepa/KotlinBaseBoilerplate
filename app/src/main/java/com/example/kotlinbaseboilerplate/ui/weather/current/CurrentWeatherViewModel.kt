package com.example.kotlinbaseboilerplate.ui.weather.current

import androidx.lifecycle.ViewModel
import com.example.kotlinbaseboilerplate.data.provider.UnitProvider
import com.example.kotlinbaseboilerplate.data.repository.ForecastRepository
import com.example.kotlinbaseboilerplate.internal.UnitSystem
import com.example.kotlinbaseboilerplate.internal.lazyDeferred
import com.example.kotlinbaseboilerplate.ui.base.WeatherViewModel

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : WeatherViewModel(forecastRepository, unitProvider) {

    //This needs to be called from a coroutine context, but can't do that here!
    //We need to make "weather" lazy so it can be used
    val weather by lazyDeferred {
        //forecastRepository.getCurrentWeatherData(super.isMetricUnit) //this parameter is for changing the stuff to get it from the api...
        forecastRepository.getCurrentWeatherData()
    }

    //Preservation of ViewModels if a job for a ViewModelProvider. We pass the ViewModel factory
    //into the provider.

}
