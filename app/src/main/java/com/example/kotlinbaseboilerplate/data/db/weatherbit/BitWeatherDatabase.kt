package com.example.kotlinbaseboilerplate.data.db.weatherbit

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.CurrentWeatherData
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.WeatherDescription

@Database(
    entities = [CurrentWeatherData::class, WeatherDescription::class],
    version = 1
)
abstract class BitWeatherDatabase : RoomDatabase() {
}