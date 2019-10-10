package com.example.kotlinbaseboilerplate.data.network.weatherbit.response.current


import com.google.gson.annotations.SerializedName

data class CurrentDataItem(
    @SerializedName("app_temp")
    val bitAppTemp: Double,
    @SerializedName("aqi")
    val bitAqi: Int,
    @SerializedName("city_name")
    val bitCityName: String,
    @SerializedName("clouds")
    val bitClouds: Int,
    @SerializedName("country_code")
    val bitCountryCode: String,
    @SerializedName("datetime")
    val bitDatetime: String,
    @SerializedName("dewpt")
    val bitDewpt: Double,
    @SerializedName("dhi")
    val bitDhi: Double,
    @SerializedName("dni")
    val bitDni: Double,
    @SerializedName("elev_angle")
    val bitElevAngle: Double,
    @SerializedName("ghi")
    val bitGhi: Double,
    @SerializedName("h_angle")
    val bitHAngle: Int,
    @SerializedName("last_ob_time")
    val bitLastObTime: String,
    @SerializedName("lat")
    val bitLat: Double,
    @SerializedName("lon")
    val bitLon: Double,
    @SerializedName("ob_time")
    val bitObTime: String,
    @SerializedName("pod")
    val bitPod: String,
    @SerializedName("precip")
    val bitPrecip: Int,
    @SerializedName("pres")
    val bitPres: Double,
    @SerializedName("rh")
    val bitRh: Int,
    @SerializedName("slp")
    val bitSlp: Double,
    @SerializedName("snow")
    val bitSnow: Int,
    @SerializedName("solar_rad")
    val bitSolarRad: Double,
    @SerializedName("state_code")
    val bitStateCode: String,
    @SerializedName("station")
    val bitStation: String,
    @SerializedName("sunrise")
    val bitSunrise: String,
    @SerializedName("sunset")
    val bitSunset: String,
    @SerializedName("temp")
    val bitTemp: Double,
    @SerializedName("timezone")
    val bitTimezone: String,
    @SerializedName("ts")
    val bitTs: Int,
    @SerializedName("uv")
    val bitUv: Double,
    @SerializedName("vis")
    val bitVis: Double,
    @SerializedName("weather")
    val bitWeather: CurrentWeather,
    @SerializedName("wind_cdir")
    val bitWindCdir: String,
    @SerializedName("wind_cdir_full")
    val bitWindCdirFull: String,
    @SerializedName("wind_dir")
    val bitWindDir: Int,
    @SerializedName("wind_spd")
    val bitWindSpd: Double
)