package com.example.kotlinbaseboilerplate.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.kotlinbaseboilerplate.R
import com.example.kotlinbaseboilerplate.utils.toastLong
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

private const val MY_PERMISSION_ACCESS_COARSE_LOCATION = 1

class MainActivity : AppCompatActivity(), KodeinAware {

    //We apply DI here to get the fused location provider to observe on a location callback
    override val kodein by closestKodein()
    //This provider client is caching the location automatically
    private val fusedLocationProviderClient: FusedLocationProviderClient by instance()
    //We just check the location periodically
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {
            super.onLocationResult(p0)
        }
    }

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Set the action toolbar for MainActiivty
        setSupportActionBar(toolbar)

        //Assign the host fragment to the navigation controller
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        //Link the bottom navigation to the navigation controller
        bottom_nav.setupWithNavController(navController)

        //Link the action bar to the navigation controller (for back button in the bar)
        NavigationUI.setupActionBarWithNavController(this, navController)

        requestLocationPermission()

        if(hasLocationPermission()){
            bindLocationManager()
        }else{
            requestLocationPermission()
        }
    }

    /**
     * This is not really required, so you can do it without an additional provider
     * There's no point of using DI in this case, since it's a simple instantiation
     */
    private fun bindLocationManager() {
        LifeCycleBoundLocationManager(this, fusedLocationProviderClient, locationCallback)
    }

    /**
     * Check if it has location permission
     */
    private fun hasLocationPermission() : Boolean {
        return ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Assign the navController to the default toolbar navigate up action
     */
    override fun onSupportNavigateUp(): Boolean {

        //NOTE: since there is no drawerLayout, set it to null
        return NavigationUI.navigateUp(navController, null)
    }

    /**
     * ask for permission on access
     */
    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            MY_PERMISSION_ACCESS_COARSE_LOCATION
        )
    }

    /**
     * check if the request code is our permission core
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == MY_PERMISSION_ACCESS_COARSE_LOCATION){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                bindLocationManager()
            else
                this.toastLong("Please, set location manually in setting")
        }
    }
}
