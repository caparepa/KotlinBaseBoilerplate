package com.example.kotlinbaseboilerplate.data.db.weatherbit

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.current.BIT_WEATHER_DATA_ID
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.current.BitCurrentWeatherData

@Dao
interface BitCurrentWeatherDataDao {
    //We create an "upsert" function (update or insert) for the current weather
    //The onConflict strategy is used for the upsert (if there is a record, replace it)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: BitCurrentWeatherData)

    //In the tutorial, there are 2 interfaces that wrap the CurrentWeatherEntity
    //in order to get the units (imperial or metric). Since the API changed, we'll use
    //the original Entity
    @Query("select * from bit_weather_data where id = $BIT_WEATHER_DATA_ID")
    fun getCurrentWeatherData() : LiveData<BitCurrentWeatherData>
}