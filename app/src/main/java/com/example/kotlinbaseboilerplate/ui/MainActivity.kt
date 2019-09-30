package com.example.kotlinbaseboilerplate.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.kotlinbaseboilerplate.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

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
    }

    override fun onSupportNavigateUp(): Boolean {
        //Assign the navController to the default toolbar navigate up action
        //NOTE: since there is no drawerLayout, set it to null
        return NavigationUI.navigateUp(navController, null)
    }
}
