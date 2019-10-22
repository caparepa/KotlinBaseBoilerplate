package com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.forecast


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.current.WeatherDescription
import com.google.gson.annotations.SerializedName

@Entity(tableName = "future_weather", indices = [Index(value = ["datetime"], unique = true)])
data class ForecastWeatherData(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @SerializedName("app_max_temp")
    val bitAppMaxTemp: Double,
    @SerializedName("app_min_temp")
    val bitAppMinTemp: Double,
    @SerializedName("clouds")
    val bitClouds: Int,
    @SerializedName("clouds_hi")
    val bitCloudsHi: Int,
    @SerializedName("clouds_low")
    val bitCloudsLow: Int,
    @SerializedName("clouds_mid")
    val bitCloudsMid: Int,
    @SerializedName("datetime")
    val bitDatetime: String,
    @SerializedName("dewpt")
    val bitDewpt: Double,
    @SerializedName("high_temp")
    val bitHighTemp: Double,
    @SerializedName("low_temp")
    val bitLowTemp: Double,
    @SerializedName("max_dhi")
    val bitMaxDhi: Any,
    @SerializedName("max_temp")
    val bitMaxTemp: Double,
    @SerializedName("min_temp")
    val bitMinTemp: Double,
    @SerializedName("moon_phase")
    val bitMoonPhase: Double,
    @SerializedName("moonrise_ts")
    val bitMoonriseTs: Int,
    @SerializedName("moonset_ts")
    val bitMoonsetTs: Int,
    @SerializedName("ozone")
    val bitOzone: Double,
    @SerializedName("pop")
    val bitPop: Int,
    @SerializedName("precip")
    val bitPrecip: Double,
    @SerializedName("pres")
    val bitPres: Double,
    @SerializedName("rh")
    val bitRh: Int,
    @SerializedName("slp")
    val bitSlp: Double,
    @SerializedName("snow")
    val bitSnow: Int,
    @SerializedName("snow_depth")
    val bitSnowDepth: Int,
    @SerializedName("sunrise_ts")
    val bitSunriseTs: Int,
    @SerializedName("sunset_ts")
    val bitSunsetTs: Int,
    @SerializedName("temp")
    val bitTemp: Double,
    @SerializedName("ts")
    val bitTs: Int,
    @SerializedName("uv")
    val bitUv: Double,
    @SerializedName("valid_date")
    val bitValidDate: String,
    @SerializedName("vis")
    val bitVis: Double,
    @SerializedName("weather")
    @Embedded(prefix = "description_")
    val bitWeather: WeatherDescription,
    @SerializedName("wind_cdir")
    val bitWindCdir: String,
    @SerializedName("wind_cdir_full")
    val bitWindCdirFull: String,
    @SerializedName("wind_dir")
    val bitWindDir: Int,
    @SerializedName("wind_gust_spd")
    val bitWindGustSpd: Double,
    @SerializedName("wind_spd")
    val bitWindSpd: Double
)