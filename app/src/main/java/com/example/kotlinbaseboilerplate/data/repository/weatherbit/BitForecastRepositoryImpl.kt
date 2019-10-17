package com.example.kotlinbaseboilerplate.data.repository.weatherbit

import androidx.lifecycle.LiveData
import com.example.kotlinbaseboilerplate.data.db.weatherbit.BitCurrentWeatherDataDao
import com.example.kotlinbaseboilerplate.data.db.weatherbit.BitWeatherDescriptionDao
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.current.BitCurrentWeatherData
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.current.BitWeatherDescription
import com.example.kotlinbaseboilerplate.data.network.weatherbit.BitWeatherNetworkDataSource
import com.example.kotlinbaseboilerplate.data.provider.LocationProvider
import org.threeten.bp.ZonedDateTime

class BitForecastRepositoryImpl(
    private val currentBitCurrentWeatherDataDao: BitCurrentWeatherDataDao,
    private val weatherDescriptionDao: BitWeatherDescriptionDao,
    private val weatherBitWeatherNetworkDataSource: BitWeatherNetworkDataSource,
    private val locationProvider: LocationProvider
) : BitForecastRepository {
    override suspend fun getBitCurrentWeatherData(): LiveData<BitCurrentWeatherData> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getBitWeatherDescription(): LiveData<BitWeatherDescription> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * This will update the livedata with a new current weather query due the observer in the
     * init block of this class, and pass dummy location and units
     */
    private suspend fun fetchCurrentWeather() {
        weatherBitWeatherNetworkDataSource.fetchBitCurrentWeather(
            locationProvider.getPreferredLocationString(), "es", "m"
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