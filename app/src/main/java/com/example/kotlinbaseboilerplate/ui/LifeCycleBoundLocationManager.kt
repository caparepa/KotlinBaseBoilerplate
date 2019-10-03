package com.example.kotlinbaseboilerplate.ui

import android.annotation.SuppressLint
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest

class LifeCycleBoundLocationManager(
    lifecycleOwner: LifecycleOwner,
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val locationCallback: LocationCallback
) : LifecycleObserver {

    //We add an observer to the owner of the lifecycle (the activity in this case)
    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    /**
     * We instantiate a new location request
     */
    private val locationRequest = LocationRequest().apply {
        interval = 5000 //Five-second interval
        fastestInterval = 5000 //Five-second max interval
        priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY //Power priority
    }

    /**
     * We start updating the location, but we need to call it from somewhere
     * so we associate with the lifecycle
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    /**
     * Remove/stop the location updates
     * To stop the updates, this function will be called from the onPause() lifecycle method
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun removeLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }
}