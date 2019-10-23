package com.example.kotlinbaseboilerplate.data.db.weatherbit.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.forecast.ForecastWeatherData
import org.threeten.bp.LocalDate

@Dao
interface FutureWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(forecastWeatherEntries: List<ForecastWeatherData>)

    @Query("select * from future_weather where date(bitDatetime) >= date(:startDate)")
    fun getFutureWeatherDetail(startDate: LocalDate): LiveData<List<ForecastWeatherData>>

    @Query("select count(id) from future_weather where date(bitDatetime) >= date(:startDate)")
    fun countFutureWeather(startDate: LocalDate) : Int

    @Query("delete from future_weather where date(bitDatetime) < date(:firstDateToKeep)")
    fun deleteOldEntries(firstDateToKeep: LocalDate)

}