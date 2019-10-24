package com.example.kotlinbaseboilerplate.data.repository

import androidx.lifecycle.LiveData
import com.example.kotlinbaseboilerplate.data.db.weatherbit.dao.CurrentWeatherDataDao
import com.example.kotlinbaseboilerplate.data.db.weatherbit.dao.FutureWeatherDao
import com.example.kotlinbaseboilerplate.data.db.weatherbit.dao.WeatherDescriptionDao
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.current.CurrentWeatherData
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.current.WeatherDescription
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.forecast.ForecastWeatherData
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.forecast.ForecastWeatherLocationData
import com.example.kotlinbaseboilerplate.data.network.weatherbit.WeatherNetworkDataSource
import com.example.kotlinbaseboilerplate.data.network.weatherbit.response.current.CurrentWeatherResponse
import com.example.kotlinbaseboilerplate.data.network.weatherbit.response.forecast.ForecastWeatherResponse
import com.example.kotlinbaseboilerplate.data.provider.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import org.threeten.bp.ZonedDateTime
import java.util.*

class ForecastRepositoryImpl(
    private val currentBitCurrentWeatherDataDao: CurrentWeatherDataDao,
    private val futureWeatherDao: FutureWeatherDao,
    private val weatherDescriptionDao: WeatherDescriptionDao,
    private val weatherbitNetworkDataSource: WeatherNetworkDataSource,
    private val bitLocationProvider: LocationProvider
) : ForecastRepository {

    /**
     * We'll implement the synchrony between the dao and the livedata, so we'll initialize the
     * network data source
     */
    init {
        //We get the current weather to be observed forever because repositories DON'T have lifecycles}
        weatherbitNetworkDataSource.apply {
            downloadedCurrentWeather.observeForever { newCurrentWeather ->
                persistFetchedCurrentWeather(newCurrentWeather)
            }
            downloadedFutureWeather.observeForever { newFutureWeather->
                persistFetchedFutureWeather(newFutureWeather)
            }
        }
    }

    override suspend fun getCurrentWeatherData(): LiveData<CurrentWeatherData> {
        //Here we call the Coroutine with context because here we return a value, unlike the persist
        //method below, where there is no return value. Also, we prevent the use of generics present
        //with specific objects of the same type (e.g. ImperialUnitWeather vs MetricUnitWeather)
        return withContext(Dispatchers.IO) {
            //since we only have one method for getting the weather, let's use it
            initWeatherData() //TODO: DON'T FORGET THIS!!! D'UH!
            return@withContext currentBitCurrentWeatherDataDao.getCurrentWeatherData()
        }
    }

    override suspend fun getFutureWeatherList(startDate: LocalDate): LiveData<out List<ForecastWeatherData>> {
        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext futureWeatherDao.getFutureWeatherDetail(startDate)
        }
    }

    override suspend fun getWeatherDescription(): LiveData<WeatherDescription> {
        return withContext(Dispatchers.IO) {
            return@withContext weatherDescriptionDao.getWeatherDescription()
        }
    }

    override suspend fun getForecastLocation(): LiveData<ForecastWeatherLocationData> {
        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext futureWeatherDao.getFutureWeatherLocationData()
        }
    }

    /**
     * Method for persisting the response in the livedata using Coroutines
     */
    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse) {

        //We use the GlobalScope to launch the coroutine, which can be used here because there is no
        //lifecycles, like activities or fragments
        //The Dispatchers.IO ensures that we can use N amount of threads
        //EDIT: now we can persist the weather location object from the response
        GlobalScope.launch(Dispatchers.IO) {
            val data = fetchedWeather.bitData[0]
            currentBitCurrentWeatherDataDao.upsert(data)
            weatherDescriptionDao.upsert(data.bitWeather)
        }
    }

    private fun persistFetchedFutureWeather(fetchedWeather: ForecastWeatherResponse) {

        //We create a local function to delete the old data
        fun deleteOldForecastData() {
            val today = LocalDate.now()
            futureWeatherDao.deleteOldEntries(today)
        }

        GlobalScope.launch(Dispatchers.IO) {
            deleteOldForecastData()
            val futureWeatherList = fetchedWeather.bitEntries.forecastWeatherList

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
     * Here we initialize the weather data by checking whether it needs to be updated or not
     */
    private suspend fun initWeatherData() {

        //FIXME: if something return LiveData is not really synchronous, so getting the value will always be null
        //val lastWeatherLocation = currentBitCurrentWeatherDataDao.getCurrentWeatherData().value //We get the LiveData value

        //FIXME: to fix it, we change it to get the object per se
        val lastWeatherLocation = currentBitCurrentWeatherDataDao.getCurrentWeatherDataNonLive()

        //In case the app is opened for the first time, fetch the current weather and return
        if (lastWeatherLocation == null || bitLocationProvider.hasLocationChanged(lastWeatherLocation)
        ) {
            fetchCurrentWeather()
            fetchFutureWeather() //we add the function to fetch the future weather
            return
        }

        //If we want to get the weather in a new location if the user moves or changes,
        //a location provider is made (since repositories don't know nor care about business logic)

        //In case there is already a fetched weather, get the current one (updated)
        val x = lastWeatherLocation.zonedDateTime
        if (isFetchCurrentNeeded(lastWeatherLocation.zonedDateTime))
            fetchCurrentWeather()

        if(isFetchFutureNeeded())
            fetchFutureWeather()
    }


    /**
     * This will update the livedata with a new current weather query due the observer in the
     * init block of this class, and pass dummy location and units
     */
    private suspend fun fetchCurrentWeather() {
        weatherbitNetworkDataSource.fetchCurrentWeather(
            bitLocationProvider.getPreferredLocationString(), Locale.getDefault().language, "M"
        )
    }


    private suspend fun fetchFutureWeather() {
        weatherbitNetworkDataSource.fetchFutureWeather(
            bitLocationProvider.getPreferredLocationString(), Locale.getDefault().language, "M"
        )
    }

    /**
     * Here we check is a new current weather is needed (30 minutes updates)
     */
    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }


    private fun isFetchFutureNeeded(): Boolean {
        val today = LocalDate.now()
        val futureWeatherCount = futureWeatherDao.countFutureWeather(today)
        return futureWeatherCount < 16
    }
}