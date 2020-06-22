package com.example.kotlinbaseboilerplate.ui.weather.future.list

import com.example.kotlinbaseboilerplate.data.provider.UnitProvider
import com.example.kotlinbaseboilerplate.data.repository.original.ForecastRepository
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

    val weatherLocation by lazyDeferred {
        forecastRepository.getForecastLocation()
    }
}
