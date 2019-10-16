package com.example.kotlinbaseboilerplate.data.db.weatherbit

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.BIT_WEATHER_DESCRIPTION_ID
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.BitWeatherDescription

@Dao
interface BitWeatherDescriptionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherLocation: BitWeatherDescription)

    @Query("select * from weather_description where id = $BIT_WEATHER_DESCRIPTION_ID")
    fun getWeatherDescription() : LiveData<BitWeatherDescription>

}