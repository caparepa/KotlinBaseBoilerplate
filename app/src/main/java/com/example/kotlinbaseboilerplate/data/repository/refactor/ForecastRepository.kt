package com.example.kotlinbaseboilerplate.data.repository.refactor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlinbaseboilerplate.data.WeatherBitApiService
import com.example.kotlinbaseboilerplate.data.db.weatherbit.WeatherDatabase
import com.example.kotlinbaseboilerplate.data.db.weatherbit.dao.CurrentWeatherDataDao
import com.example.kotlinbaseboilerplate.data.db.weatherbit.dao.FutureWeatherDao
import com.example.kotlinbaseboilerplate.data.db.weatherbit.dao.WeatherDescriptionDao
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.current.WeatherDescription
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.forecast.ForecastWeatherData
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.forecast.ForecastWeatherLocationData
import com.example.kotlinbaseboilerplate.data.network.refactor.SafeApiRequest
import com.example.kotlinbaseboilerplate.data.network.weatherbit.response.forecast.ForecastWeatherResponse
import com.example.kotlinbaseboilerplate.data.provider.PreferenceProvider
import com.example.kotlinbaseboilerplate.utils.Coroutines
import com.example.kotlinbaseboilerplate.utils.FUTURE_DAYS_FETCH
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate

class ForecastRepository(
    private val wbApi: WeatherBitApiService,
    private val wbDb: WeatherDatabase,
    private val wbPrefs: PreferenceProvider,
    private val futureWeatherDao: FutureWeatherDao,
    private val weatherDescriptionDao: WeatherDescriptionDao
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

    private fun saveForecastList(forecast: List<ForecastWeatherData>) {

    }

    private fun persistFetchedFutureWeather(fetchedWeather: ForecastWeatherResponse) {

        //We create a local function to delete the old data
        fun deleteOldForecastData() {
            val today = LocalDate.now()
            futureWeatherDao.deleteOldEntries(today)
        }

        Coroutines.io {
            deleteOldForecastData()
            val futureWeatherList = fetchedWeather.bitEntries

            //create a new object fot the location
            val futureWeatherLocationData = ForecastWeatherLocationData(
                fetchedWeather.bitCityName,
                fetchedWeather.bitCountryCode,
                fetchedWeather.bitStateCode,
                fetchedWeather.bitTimezone
            )

            //upsert the future weather location
            futureWeatherDao.upsertLocationData(futureWeatherLocationData)

            //insert the forecast list
            futureWeatherDao.insert(futureWeatherList)

            //NOTE: since the response doesn't have weather description outside the list,
            //we use the first element of said list to get the description for the location,
            //which kinda sucks for this API response structure
            weatherDescriptionDao.upsert(futureWeatherList[0].bitWeather)
        }
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