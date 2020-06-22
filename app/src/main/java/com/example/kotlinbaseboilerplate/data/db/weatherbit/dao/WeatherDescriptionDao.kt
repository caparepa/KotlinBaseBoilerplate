package com.example.kotlinbaseboilerplate.data.db.weatherbit.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.current.WeatherDescription
import com.example.kotlinbaseboilerplate.utils.WEATHER_DESCRIPTION_ID

@Dao
interface WeatherDescriptionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherLocation: WeatherDescription)

    @Query("select * from bit_weather_description where id = $WEATHER_DESCRIPTION_ID")
    fun getWeatherDescription() : LiveData<WeatherDescription>

}