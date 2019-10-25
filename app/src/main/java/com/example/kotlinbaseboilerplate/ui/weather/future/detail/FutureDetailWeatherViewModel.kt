package com.example.kotlinbaseboilerplate.ui.weather.future.detail

import androidx.lifecycle.ViewModel
import com.example.kotlinbaseboilerplate.data.provider.UnitProvider
import com.example.kotlinbaseboilerplate.data.repository.ForecastRepository
import com.example.kotlinbaseboilerplate.internal.lazyDeferred
import com.example.kotlinbaseboilerplate.ui.base.WeatherViewModel
import org.threeten.bp.LocalDate

class FutureDetailWeatherViewModel(
    private val detailDate: LocalDate,
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : WeatherViewModel(forecastRepository, unitProvider) {

    val weather by lazyDeferred {
        //TODO: remember to modify stuff in order to get the metric stuff!
        //forecastRepository.getFutureWeatherByDate(detailDate, super.isMetricUnit)
        forecastRepository.getFutureWeatherByDate(detailDate)
    }

}
