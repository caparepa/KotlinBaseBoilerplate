package com.example.kotlinbaseboilerplate.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kotlinbaseboilerplate.data.db.entity.CURRENT_LOCATION_ID
import com.example.kotlinbaseboilerplate.data.db.entity.CurrentWeatherLocation

@Dao
interface CurrentWeatherLocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(currentWeatherLocation: CurrentWeatherLocation)

    @Query("select * from current_location where id = $CURRENT_LOCATION_ID")
    fun getCurrentLocation() : LiveData<CurrentWeatherLocation>

}