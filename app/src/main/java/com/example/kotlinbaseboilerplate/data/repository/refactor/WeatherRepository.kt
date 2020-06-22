package com.example.kotlinbaseboilerplate.data.repository.refactor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlinbaseboilerplate.data.WeatherBitApiService
import com.example.kotlinbaseboilerplate.data.db.weatherbit.WeatherDatabase
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.current.CurrentWeatherData
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.current.WeatherDescription
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.forecast.ForecastWeatherData
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.forecast.ForecastWeatherLocationData
import com.example.kotlinbaseboilerplate.data.network.refactor.SafeApiRequest
import com.example.kotlinbaseboilerplate.data.provider.PreferenceProvider
import org.threeten.bp.LocalDate
import org.threeten.bp.ZonedDateTime

class WeatherRepository(
    private val wbApi: WeatherBitApiService,
    private val wbDb: WeatherDatabase,
    private val wbPrefs: PreferenceProvider
) : SafeApiRequest() {

    private val weatherData = MutableLiveData<CurrentWeatherData>()

    init {

    }

    /**
     * Public functions that will be called from the ViewModel
     */

    suspend fun getCurrentWeather(): LiveData<out CurrentWeatherData>? {
        return null
    }

    suspend fun getWeatherDescription(): LiveData<WeatherDescription>? {
        return null
    }

    /**
     * Private functions to be used in the repository
     */

    private suspend fun fetchCurrentWeather() {

    }

    /**
     * Validation
     */

    private fun isFetchNeeded(lastFetchedTime: ZonedDateTime): Boolean {
        return false
    }


}