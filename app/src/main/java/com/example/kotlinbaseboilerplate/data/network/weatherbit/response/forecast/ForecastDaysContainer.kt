package com.example.kotlinbaseboilerplate.data.network.weatherbit.response.forecast

import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.forecast.ForecastWeatherData

data class ForecastDaysContainer(
    val forecastWeatherList: List<ForecastWeatherData>
) {
}