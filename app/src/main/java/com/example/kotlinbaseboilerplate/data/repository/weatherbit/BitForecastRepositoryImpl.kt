package com.example.kotlinbaseboilerplate.data.repository.weatherbit

import androidx.lifecycle.LiveData
import com.example.kotlinbaseboilerplate.data.db.weatherbit.dao.CurrentWeatherDataDao
import com.example.kotlinbaseboilerplate.data.db.weatherbit.dao.WeatherDescriptionDao
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.current.CurrentWeatherData
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.current.WeatherDescription
import com.example.kotlinbaseboilerplate.data.network.weatherbit.WeatherNetworkDataSource
import com.example.kotlinbaseboilerplate.data.network.weatherbit.response.current.CurrentWeatherResponse
import com.example.kotlinbaseboilerplate.data.provider.weatherbit.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class BitForecastRepositoryImpl(
    private val currentBitCurrentWeatherDataDao: CurrentWeatherDataDao,
    private val weatherDescriptionDao: WeatherDescriptionDao,
    private val weatherBitWeatherNetworkDataSource: WeatherNetworkDataSource,
    private val bitLocationProvider: LocationProvider
) : BitForecastRepository {

    /**
     * We'll implement the synchrony between the dao and the livedata, so we'll initialize the
     * network data source
     */
    init {
        //We get the current weather to be observed forever because repositories DON'T have lifecycles}
        weatherBitWeatherNetworkDataSource.downloadedCurrentWeather.observeForever { newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)
        }
    }

    override suspend fun getBitCurrentWeatherData(): LiveData<CurrentWeatherData> {
        //Here we call the Coroutine with context because here we return a value, unlike the persist
        //method below, where there is no return value. Also, we prevent the use of generics present
        //with specific objects of the same type (e.g. ImperialUnitWeather vs MetricUnitWeather)
        return withContext(Dispatchers.IO) {
            //since we only have one method for getting the weather, let's use it
            initWeatherData() //TODO: DON'T FORGET THIS!!! D'UH!
            return@withContext currentBitCurrentWeatherDataDao.getCurrentWeatherData()
        }
    }

    override suspend fun getBitWeatherDescription(): LiveData<WeatherDescription> {
        return withContext(Dispatchers.IO) {
            return@withContext weatherDescriptionDao.getWeatherDescription()
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

    /**
     * Here we initialize the weather data by checking whether it needs to be updated or not
     */
    private suspend fun initWeatherData() {

        val lastWeatherLocation = currentBitCurrentWeatherDataDao.getCurrentWeatherData()
            .value //We get the LiveData value

        val xx = lastWeatherLocation.toString()

        //In case the app is opened for the first time, fetch the current weather and return
        if (lastWeatherLocation == null || bitLocationProvider.hasLocationChanged(lastWeatherLocation)
        ) {
            fetchCurrentWeather()
            return
        }

        //If we want to get the weather in a new location if the user moves or changes,
        //a location provider is made (since repositories don't know nor care about business logic)

        //In case there is already a fetched weather, get the current one (updated)
        val x = lastWeatherLocation.zonedDateTime
        if (isFetchCurrentNeeded(lastWeatherLocation.zonedDateTime))
            fetchCurrentWeather()
    }


    /**
     * This will update the livedata with a new current weather query due the observer in the
     * init block of this class, and pass dummy location and units
     */
    private suspend fun fetchCurrentWeather() {
        val y = bitLocationProvider.getPreferredLocationString()
        weatherBitWeatherNetworkDataSource.fetchCurrentWeather(
            bitLocationProvider.getPreferredLocationString(), "en", "M"
        )
    }

    /**
     * Here we check is a new current weather is needed (30 minutes updates)
     */
    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}