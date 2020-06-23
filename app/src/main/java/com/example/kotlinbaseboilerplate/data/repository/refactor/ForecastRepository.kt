package com.example.kotlinbaseboilerplate.data.repository.refactor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlinbaseboilerplate.data.ApiService
import com.example.kotlinbaseboilerplate.data.db.weatherbit.WeatherDatabase
import com.example.kotlinbaseboilerplate.data.db.weatherbit.dao.CurrentWeatherDataDao
import com.example.kotlinbaseboilerplate.data.db.weatherbit.dao.FutureWeatherDao
import com.example.kotlinbaseboilerplate.data.db.weatherbit.dao.WeatherDescriptionDao
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.current.WeatherDescription
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.forecast.ForecastWeatherData
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.forecast.ForecastWeatherLocationData
import com.example.kotlinbaseboilerplate.data.network.refactor.SafeApiRequest
import com.example.kotlinbaseboilerplate.data.provider.LocationProvider
import com.example.kotlinbaseboilerplate.data.provider.PreferenceProvider
import com.example.kotlinbaseboilerplate.utils.Coroutines
import com.example.kotlinbaseboilerplate.utils.FUTURE_DAYS_FETCH
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import java.util.*

class ForecastRepository(
    private val wbApi: ApiService,
    private val wbDb: WeatherDatabase,
    private val wbPrefs: PreferenceProvider,
    private val locationProvider: LocationProvider,
    private val weatherDataDao: CurrentWeatherDataDao,
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
        return withContext(Dispatchers.IO) {
            initForecastData()
            //return@withContext futureWeatherDao.getFutureWeatherDetail(startDate)
            wbDb.getFutureWeatherDao().getFutureWeatherDetail(startDate)
        }
    }

    suspend fun getForecastByDate(date: LocalDate): LiveData<out ForecastWeatherData>? {
        return withContext(Dispatchers.IO) {
            initForecastData()
            //return@withContext futureWeatherDao.getDetailWeatherByDate(date)
            wbDb.getFutureWeatherDao().getDetailWeatherByDate(date)
        }
    }

    suspend fun getForecastDescription(): LiveData<WeatherDescription>? {
        return withContext(Dispatchers.IO) {
            //return@withContext weatherDescriptionDao.getWeatherDescription()
            wbDb.getWeatherDescriptionDao().getWeatherDescription()
        }
    }

    suspend fun getForecastLocation(): LiveData<ForecastWeatherLocationData>? {
        return withContext(Dispatchers.IO) {
            initForecastData()
            //return@withContext futureWeatherDao.getFutureWeatherLocationData()
            wbDb.getFutureWeatherDao().getFutureWeatherLocationData()
        }
    }

    /**
     * Private functions to be used in the repository
     */

    private suspend fun initForecastData() {
        //FIXME: if something return LiveData is not really synchronous, so getting the value will always be null
        //val lastWeatherLocation = currentBitCurrentWeatherDataDao.getCurrentWeatherData().value //We get the LiveData value

        //FIXME: to fix it, we change it to get the object per se
        val lastWeatherLocation = weatherDataDao.getCurrentWeatherDataNonLive()

        //In case the app is opened for the first time, fetch the current weather and return
        if (lastWeatherLocation == null || locationProvider.hasLocationChanged(lastWeatherLocation)
        ) {
            fetchForecast() //we add the function to fetch the future weather
            return
        }

        //If we want to get the weather in a new location if the user moves or changes,
        //a location provider is made (since repositories don't know nor care about business logic)

        //In case there is already a fetched weather, get the current one (updated)
        val x = lastWeatherLocation.zonedDateTime

        if(isFetchNeeded())
            fetchForecast()
    }

    private fun saveForecastList(forecast: List<ForecastWeatherData>) {

    }

    private fun fetchForecast() {

        //We create a local function to delete the old data
        fun deleteOldForecastData() {
            val today = LocalDate.now()
            futureWeatherDao.deleteOldEntries(today)
        }

        Coroutines.io {

            deleteOldForecastData()

            val fetchedWeather = apiRequest {
                wbApi.getForecastWeather(
                    locationProvider.getPreferredLocationString(),
                    Locale.getDefault().language, "M"
                )
            }

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