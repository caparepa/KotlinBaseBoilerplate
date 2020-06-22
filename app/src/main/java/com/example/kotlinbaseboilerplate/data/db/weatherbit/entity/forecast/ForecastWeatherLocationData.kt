package com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.forecast

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kotlinbaseboilerplate.utils.FUTURE_WEATHER_LOCATION_DATA_ID
import com.google.gson.annotations.SerializedName

@Entity(tableName = "future_weather_location_data")
data class ForecastWeatherLocationData(
    @SerializedName("city_name")
    val bitCityName: String,
    @SerializedName("country_code")
    val bitCountryCode: String,
    @SerializedName("state_code")
    val bitStateCode: String,
    @SerializedName("timezone")
    val bitTimezone: String
) {

    //NOTE: since there can't be "multiple" current weather, we'll set the primary key to false
    @PrimaryKey(autoGenerate = false)
    var id: Int =
        FUTURE_WEATHER_LOCATION_DATA_ID
}