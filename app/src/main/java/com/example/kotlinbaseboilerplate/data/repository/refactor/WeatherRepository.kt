package com.example.kotlinbaseboilerplate.data.repository.refactor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlinbaseboilerplate.data.ApiService
import com.example.kotlinbaseboilerplate.data.WeatherBitApiService
import com.example.kotlinbaseboilerplate.data.db.weatherbit.WeatherDatabase
import com.example.kotlinbaseboilerplate.data.db.weatherbit.dao.CurrentWeatherDataDao
import com.example.kotlinbaseboilerplate.data.db.weatherbit.dao.FutureWeatherDao
import com.example.kotlinbaseboilerplate.data.db.weatherbit.dao.WeatherDescriptionDao
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.current.CurrentWeatherData
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.current.WeatherDescription
import com.example.kotlinbaseboilerplate.data.network.refactor.SafeApiRequest
import com.example.kotlinbaseboilerplate.data.provider.LocationProvider
import com.example.kotlinbaseboilerplate.data.provider.PreferenceProvider
import org.threeten.bp.ZonedDateTime

class WeatherRepository(
    private val wbApi: ApiService,
    private val wbDb: WeatherDatabase,
    private val wbPrefs: PreferenceProvider,
    private val locationProvider: LocationProvider,
    private val weatherDataDao: CurrentWeatherDataDao,
    private val futureWeatherDao: FutureWeatherDao,
    private val weatherDescriptionDao: WeatherDescriptionDao
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

    private fun isFetchNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }


}