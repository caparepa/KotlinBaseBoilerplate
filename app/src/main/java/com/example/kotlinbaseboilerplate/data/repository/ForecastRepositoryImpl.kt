package com.example.kotlinbaseboilerplate.data.repository

import androidx.lifecycle.LiveData
import com.example.kotlinbaseboilerplate.data.db.CurrentWeatherEntryDao
import com.example.kotlinbaseboilerplate.data.db.WeatherLocationDao
import com.example.kotlinbaseboilerplate.data.db.entity.CurrentWeatherEntry
import com.example.kotlinbaseboilerplate.data.db.entity.WeatherLocation
import com.example.kotlinbaseboilerplate.data.network.WeatherNetworkDataSource
import com.example.kotlinbaseboilerplate.data.network.response.CurrentWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

/**
 * We set constructor parameters in the interface implementation related to the current weather DAO
 * and the data source. Other parameters will follow suit later.
 * EDIT: Now we add to the constructor the WeatherLocationDao in order to get the location for this
 * repository
 */
class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherEntryDao,
    private val weatherLocationDao: WeatherLocationDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource
) : ForecastRepository {

    /**
     * We'll implement the synchrony between the dao and the livedata, so we'll initialize the
     * network data source
     */
    init {
        //We get the current weather to be observed forever because repositories DON'T have lifecycles
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever { newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)
        }
    }

    override suspend fun getCurrentWeather(): LiveData<CurrentWeatherEntry> {
        //Here we call the Coroutine with context because here we return a value, unlike the persist
        //method below, where there is no return value. Also, we prevent the use of generics present
        //with specific objects of the same type (e.g. ImperialUnitWeather vs MetricUnitWeather)
        return withContext(Dispatchers.IO) {
            //since we only have one method for getting the weather, let's use it
            initWeatherData() //TODO: DON'T FORGET THIS!!! D'UH!
            return@withContext currentWeatherDao.getCurrentWeather()
        }
    }

    //Same as getCurrentWeather(), we implement the function for getting the weather location
    override suspend fun getWeatherLocation(): LiveData<WeatherLocation> {
        return withContext(Dispatchers.IO) {
            return@withContext weatherLocationDao.getLocation()
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
            currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)
            weatherLocationDao.upsert(fetchedWeather.weatherLocation)
        }
    }

    /**
     * Here we initialize the weather data by checking whether it needs to be updated or not
     */
    private suspend fun initWeatherData() {

        val lastWeatherLocation = weatherLocationDao.getLocation().value //We get the LiveData value

        //In case the app is opened for the first time, fetch the current weather and return
        if(lastWeatherLocation == null) {
            fetchCurrentWeather()
            return
        }

        //In case there is already a fetched weather, get the current one (updated)
        if(isFetchCurrentNeeded(lastWeatherLocation.zonedDateTime))
            fetchCurrentWeather()
    }

    /**
     * This will update the livedata with a new current weather query due the observer in the
     * init block of this class, and pass dummy location and units
     */
    private suspend fun fetchCurrentWeather() {
        weatherNetworkDataSource.fetchCurrentWeather("Los Angeles", "m")
    }

    /**
     * Here we check is a new current weather is needed (30 minutes updates)
     */
    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }

}