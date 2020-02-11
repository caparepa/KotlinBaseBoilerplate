package com.example.kotlinbaseboilerplate.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.kotlinbaseboilerplate.data.WeatherBitApiService
import com.example.kotlinbaseboilerplate.data.db.weatherbit.WeatherDatabase
import com.example.kotlinbaseboilerplate.data.db.weatherbit.dao.CurrentWeatherDataDao
import com.example.kotlinbaseboilerplate.data.db.weatherbit.dao.FutureWeatherDao
import com.example.kotlinbaseboilerplate.data.db.weatherbit.dao.WeatherDescriptionDao
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.current.CurrentWeatherData
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.current.WeatherDescription
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.forecast.ForecastWeatherData
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.forecast.ForecastWeatherLocationData
import com.example.kotlinbaseboilerplate.data.network.SafeApiRequest
import com.example.kotlinbaseboilerplate.data.network.weatherbit.response.current.CurrentWeatherResponse
import com.example.kotlinbaseboilerplate.data.provider.WeatherProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZonedDateTime
import java.lang.Exception

class WeatherRepository(
    private val weatherApi: WeatherBitApiService,
    private val currentBitCurrentWeatherDataDao: CurrentWeatherDataDao,
    private val futureWeatherDao: FutureWeatherDao,
    private val weatherDescriptionDao: WeatherDescriptionDao,
    private val weatherDb: WeatherDatabase,
    private val weatherPreference: WeatherProvider
) : SafeApiRequest() {

    private val currentWeather = MutableLiveData<CurrentWeatherData>()
    private val forecastWeatherList = MutableLiveData<List<ForecastWeatherData>>()
    private val weatherByDate = MutableLiveData<ForecastWeatherData>()
    private val weatherDescription = MutableLiveData<WeatherDescription>()
    private val forecastLocation = MutableLiveData<ForecastWeatherLocationData>()


    private suspend fun fetchCurrentWeatherData() {

    }

    private suspend fun getFutureWeatherList() {

    }

    private suspend fun getFutureWeatherByDate() {

    }

    private suspend fun getWeatherDescription() {

    }

    private suspend fun getForecastLocation() {

    }

    private suspend fun initWeatherData() {

    }

    private suspend fun fetchCurrentWeather() {
        val lastSavedAt = weatherPreference.getLastSavedAt()
    }

    private suspend fun fetchFutureWeather() {

    }

    private suspend fun persistCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            val data = fetchedWeather.bitData[0]
            currentBitCurrentWeatherDataDao.upsert(data)
            weatherDescriptionDao.upsert(data.bitWeather)
            //currentBitCurrentWeatherDataDao.upsert(data)
            //weatherDescriptionDao.upsert(data.bitWeather)
        }
    }

    private suspend fun persistFutureWeather() {

    }

    private fun isFetchNeeded(savedAt: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return savedAt.isBefore(thirtyMinutesAgo)
    }

}