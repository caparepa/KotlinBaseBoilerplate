package com.example.kotlinbaseboilerplate.data.db.weatherbit.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.current.WEATHER_DATA_ID
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.current.CurrentWeatherData

@Dao
interface CurrentWeatherDataDao {
    //We create an "upsert" function (update or insert) for the current weather
    //The onConflict strategy is used for the upsert (if there is a record, replace it)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: CurrentWeatherData)

    //In the tutorial, there are 2 interfaces that wrap the CurrentWeatherEntity
    //in order to get the units (imperial or metric). Since the API changed, we'll use
    //the original Entity
    @Query("select * from bit_weather_data where id = $WEATHER_DATA_ID")
    fun getCurrentWeatherData() : LiveData<CurrentWeatherData>

    @Query("select * from bit_weather_data where id = $WEATHER_DATA_ID")
    fun getCurrentWeatherDataNonLive() : CurrentWeatherData?
}
