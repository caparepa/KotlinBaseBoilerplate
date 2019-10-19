package com.example.kotlinbaseboilerplate.data.provider.weatherbit

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.current.CurrentWeatherData
import com.example.kotlinbaseboilerplate.data.provider.PreferenceProvider
import com.example.kotlinbaseboilerplate.internal.LocationPermissionNotGrantedException
import com.example.kotlinbaseboilerplate.internal.asDeferred
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.Deferred

const val USE_DEVICE_LOCATION = "USE_DEVICE_LOCATION"
const val CUSTOM_LOCATION = "CUSTOM_LOCATION"

class LocationProviderImpl(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    context: Context
) : PreferenceProvider(context),
    LocationProvider {

    private val appContext = context.applicationContext

    /**
     * In this fun, hasDeviceLocationChanged() is awaiting on getLastDeviceLocation(), which can
     * throw an exception, so we encapsulate in a try-catch
     */
    override suspend fun hasLocationChanged(lastWeatherData: CurrentWeatherData): Boolean {
        val deviceLocationChanged = try {
            hasDeviceLocationChanged(lastWeatherData)
        } catch (e: LocationPermissionNotGrantedException) {
            return false
        }
        return deviceLocationChanged || hasCustomLocationChanged(lastWeatherData)
    }

    /**
     * We modify this method to get the preferred location using the methods located in this
     * provider
     */
    override suspend fun getPreferredLocationString(): String {
        if (isUsingDeviceLocation()) {
            try {
                //Since getLasDeviceLocation() returns a deferred, we use await
                val deviceLocation = getlastDeviceLocation().await()
                    ?: return "${getCustomLocationName()}"
                return "${deviceLocation.latitude},${deviceLocation.longitude}"
            } catch (e: LocationPermissionNotGrantedException) {
                return "${getCustomLocationName()}"
            }
        } else {
            return "${getCustomLocationName()}"
        }
    }

    private suspend fun hasDeviceLocationChanged(lastWeatherData: CurrentWeatherData): Boolean {

        //If not using device location (i.e. no permissions granted)
        if (!isUsingDeviceLocation())
            return false

        //If we are using location permision, get location; if we don't have location return false
        val deviceLocation = getlastDeviceLocation().await()
            ?: return false

        //Comparing doubles cannot be done with "=="
        val comparisonThreshold = 0.03
        return Math.abs(deviceLocation.latitude - lastWeatherData.bitLat) > comparisonThreshold &&
                Math.abs(deviceLocation.longitude - lastWeatherData.bitLon) > comparisonThreshold
    }

    private fun hasCustomLocationChanged(lastWeatherLocationData: CurrentWeatherData): Boolean {
        val customLocationName = getCustomLocationName()
        return customLocationName != lastWeatherLocationData.bitCityName
    }

    private fun isUsingDeviceLocation(): Boolean {
        return preferences.getBoolean(USE_DEVICE_LOCATION, true)
    }

    private fun getCustomLocationName(): String? {
        return preferences.getString(CUSTOM_LOCATION, null)
    }

    @SuppressWarnings("MissingPermission")
    private fun getlastDeviceLocation(): Deferred<Location?> {
        //TODO: we need to observer the fused location provider to make sure it has a location to return
        //TODO: so we go to MainActivity
        return if (hasLocationPermission()) {
            //This provider returns a Task, but we need a Deferred, so we convert it
            fusedLocationProviderClient.lastLocation.asDeferred()
        } else {
            throw LocationPermissionNotGrantedException()
        }
    }

    /**
     * Check the location permission manually
     */
    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            appContext,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

}