package com.example.kotlinbaseboilerplate.ui.weather.current

import androidx.lifecycle.ViewModel
import com.example.kotlinbaseboilerplate.data.provider.UnitProvider
import com.example.kotlinbaseboilerplate.data.repository.weatherbit.BitForecastRepository
import com.example.kotlinbaseboilerplate.internal.UnitSystem
import com.example.kotlinbaseboilerplate.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val forecastRepository: BitForecastRepository,
    unitProvider: UnitProvider
) : ViewModel() {

    //We need a way for the view to fetch the weather (a middleman)
    //The viewmodel will fetch the data from the repository and expose it to the view
    //so the view can fetch it (loose coupling)

    private val unitSystem = unitProvider.getUnitSystem()
    val isMetric: Boolean
        get() = unitSystem == UnitSystem.METRIC

    //This needs to be called from a coroutine context, but can't do that here!
    //We need to make "weather" lazy so it can be used
    val weather by lazyDeferred {
        forecastRepository.getBitCurrentWeatherData()
    }

    //We also get the weather location via lazyDeferred in the viewmodel
    val weatherDescription by lazyDeferred{
        forecastRepository.getBitWeatherDescription()
    }

    //Preservation of ViewModels if a job for a ViewModelProvider. We pass the ViewModel factory
    //into the provider.

}
