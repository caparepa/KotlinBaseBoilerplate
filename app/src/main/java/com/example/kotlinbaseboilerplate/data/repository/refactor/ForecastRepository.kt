package com.example.kotlinbaseboilerplate.data.repository.refactor

import com.example.kotlinbaseboilerplate.data.WeatherBitApiService
import com.example.kotlinbaseboilerplate.data.db.weatherbit.WeatherDatabase
import com.example.kotlinbaseboilerplate.data.network.refactor.SafeApiRequest
import com.example.kotlinbaseboilerplate.data.provider.PreferenceProvider
import com.example.kotlinbaseboilerplate.data.repository.BaseRepository

class ForecastRepository(
    private val wbApi: WeatherBitApiService,
    private val wbDb: WeatherDatabase,
    private val wbPrefs: PreferenceProvider
) : SafeApiRequest() {
}