package com.example.kotlinbaseboilerplate.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

const val CURRENT_LOCATION_ID = 0

@Entity(tableName = "current_location")
data class CurrentWeatherLocation(
    @SerializedName("country")
    val country: String,
    @SerializedName("lat")
    val lat: String,
    @SerializedName("localtime")
    val localtime: String,
    @SerializedName("localtime_epoch")
    val localtimeEpoch: Int,
    @SerializedName("lon")
    val lon: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("region")
    val region: String,
    @SerializedName("timezone_id")
    val timezoneId: String,
    @SerializedName("utc_offset")
    val utcOffset: String
) {
    //NOTE: since there can't be "multiple" current weather locations,
    // we'll set the primary key to false
    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_LOCATION_ID
}