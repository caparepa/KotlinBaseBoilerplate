package com.example.kotlinbaseboilerplate.ui.weather.future.list

import androidx.lifecycle.ViewModel
import com.example.kotlinbaseboilerplate.data.provider.UnitProvider
import com.example.kotlinbaseboilerplate.data.repository.ForecastRepository
import com.example.kotlinbaseboilerplate.internal.UnitSystem
import com.example.kotlinbaseboilerplate.internal.lazyDeferred
import com.example.kotlinbaseboilerplate.ui.base.WeatherViewModel
import org.threeten.bp.LocalDate

class FutureListWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : WeatherViewModel(forecastRepository, unitProvider) {

    val weatherEntries by lazyDeferred {
        //forecastRepository.getFutureWeatherList(LocalDate.now(), super.isMetricUnit)
        forecastRepository.getFutureWeatherList(LocalDate.now())
    }
}
