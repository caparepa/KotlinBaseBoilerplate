package com.example.kotlinbaseboilerplate.data.repository.refactor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlinbaseboilerplate.data.WeatherBitApiService
import com.example.kotlinbaseboilerplate.data.db.weatherbit.WeatherDatabase
import com.example.kotlinbaseboilerplate.data.db.weatherbit.dao.FutureWeatherDao
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.current.WeatherDescription
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.forecast.ForecastWeatherData
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.forecast.ForecastWeatherLocationData
import com.example.kotlinbaseboilerplate.data.network.refactor.SafeApiRequest
import com.example.kotlinbaseboilerplate.data.provider.PreferenceProvider
import com.example.kotlinbaseboilerplate.utils.FUTURE_DAYS_FETCH
import org.threeten.bp.LocalDate

class ForecastRepository(
    private val wbApi: WeatherBitApiService,
    private val wbDb: WeatherDatabase,
    private val wbPrefs: PreferenceProvider,
    private val futureWeatherDao: FutureWeatherDao
) : SafeApiRequest() {

    private val forecastData = MutableLiveData<ForecastWeatherData>()

    init {
        forecastData.observeForever {
            //here goes the suspend function
        }
    }

    /**
     * Public functions that will be called from the ViewModel
     */

    suspend fun getForecastList(startDate: LocalDate): LiveData<out List<ForecastWeatherData>>? {
        return null
    }

    suspend fun getForecastByDate(date: LocalDate): LiveData<out ForecastWeatherData>? {
        return null
    }

    suspend fun getForecastDescription(): LiveData<WeatherDescription>? {
        return null
    }

    suspend fun getForecastLocation(): LiveData<ForecastWeatherLocationData>? {
        return null
    }

    /**
     * Private functions to be used in the repository
     */

    private suspend fun fetchFutureWeather() {

    }

    /**
     * Validation
     */

    private fun isFetchNeeded(): Boolean {
        val today = LocalDate.now()
        val futureWeatherCount = futureWeatherDao.countFutureWeather(today)
        return futureWeatherCount < FUTURE_DAYS_FETCH
    }

}