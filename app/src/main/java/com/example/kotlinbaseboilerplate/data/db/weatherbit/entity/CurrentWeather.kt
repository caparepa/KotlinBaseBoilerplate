package com.example.kotlinbaseboilerplate.data.db.weatherbit.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

const val BIT_CURRENT_WEATHER_ID = 0

@Entity(tableName = "current_weather_desc")
data class CurrentWeather(
    @SerializedName("code")
    val bitCode: String,
    @SerializedName("description")
    val bitDescription: String,
    @SerializedName("icon")
    val bitIcon: String
) {
    //NOTE: since there can't be "multiple" current weather, we'll set the primary key to false
    @PrimaryKey(autoGenerate = false)
    var id: Int = BIT_CURRENT_WEATHER_ID
}