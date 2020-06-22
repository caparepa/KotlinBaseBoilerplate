package com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.current


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kotlinbaseboilerplate.utils.WEATHER_DESCRIPTION_ID
import com.google.gson.annotations.SerializedName

@Entity(tableName = "bit_weather_description")
data class WeatherDescription(
    @SerializedName("code")
    val bitCode: String,
    @SerializedName("description")
    val bitDescription: String,
    @SerializedName("icon")
    val bitIcon: String
) {
    //NOTE: since there can't be "multiple" current weather, we'll set the primary key to false
    @PrimaryKey(autoGenerate = false)
    var id: Int =
        WEATHER_DESCRIPTION_ID
}