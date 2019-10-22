package com.example.kotlinbaseboilerplate.data.db.weatherbit.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.forecast.ForecastWeatherData

@Dao
interface FutureWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(forecastWeatherEntries: List<ForecastWeatherData>)

    @Query("select * from future_weather where date(bitDatetime) >= date(:startDate)")
    fun getFutureWeatherDetail(startDate: String): LiveData<List<ForecastWeatherData>>

    @Query("select count(id) from future_weather where date(bitDatetime) >= date(:startDate)")
    fun countFutureWeather(startDate: String) : Int

    @Query("delete from future_weather where date(bitDatetime) < date(:firstDateToKeep)")
    fun deleteOldEntries(firstDateToKeep: String)

}