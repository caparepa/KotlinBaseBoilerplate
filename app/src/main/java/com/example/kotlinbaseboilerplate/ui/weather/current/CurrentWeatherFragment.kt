package com.example.kotlinbaseboilerplate.ui.weather.current

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import com.example.kotlinbaseboilerplate.R
import com.example.kotlinbaseboilerplate.internal.glide.GlideApp
import com.example.kotlinbaseboilerplate.ui.base.ScopedFragment
import com.example.kotlinbaseboilerplate.utils.makeGone
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


/**
 * We make the fragment a subclass of scoped fragment so we can use the coroutine scope safely
 */
class CurrentWeatherFragment : ScopedFragment(), KodeinAware {

    //We need to make the fragment aware to KodeinAware in order to use DI
    //we instantiate kodein by using the closestKodein(), in this case, the one in ForecastApplication
    //You could use local kodein in fragments, etc, if you want to override specifics
    override val kodein by closestKodein()
    private val viewModelFactory: CurrentWeatherViewModelFactory by instance() //cast explictly!!!

    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(CurrentWeatherViewModel::class.java)

        bindUI()
    }

    //Since we need to get the data from the viewModel, we have to do it from a coroutine scope...
    //But we can't use coroutine scope in classes with lifecycles, so we use a local scope
    //by creating a base fragment and extending from it
    private fun bindUI() = launch {
        //we fetch the data from the viewmodel
        val currentWeather = viewModel.weather.await()
        val weatherLocation = viewModel.weatherLocation.await()

        //we observe the livedata within the fragment lifecycle, and in the observer we set the
        //ui interaction
        currentWeather.observe(this@CurrentWeatherFragment, Observer {
            if (it == null) return@Observer //if there is no data, return the observer until there is data!

            //Hide the loading group
            group_loading.makeGone()

            //update toolbar

            updateDateToToday()
            updateTemperature(it.temperature, it.feelslike)
            updatePrecipitation(it.precip)
            updateWind(it.windDir, it.windSpeed)
            updateVisibility(it.visibility)

            //Set up Glide module inside the observer, so it can load the weather image
            //into the imageView
            GlideApp.with(this@CurrentWeatherFragment)
                .load(it.weatherIcons[0])
                .into(imageView_condition_icon)

            //TODO: modify code to get unit from request!

        })

        //Just as we have an observer for the weather data, we make an observer for the weather
        //location, and we pass its name in order to fetch it from the ViewModel
        weatherLocation.observe(this@CurrentWeatherFragment, Observer { location ->
            if (location == null) return@Observer
            updateLocation(location.name)
        })
    }

    //TODO: STUB!
    private fun chooseLocalizedUnitAbbreviation(metric: String, imperial: String): String {
        return "m"
    }


    private fun updateLocation(location: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDateToToday() {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Today"
    }

    //TODO: check later how to implement all the structures for the weather request for the units (metric or imperial)
    private fun updateTemperature(temperature: Double, feelsLike: Double) {
        textView_temperature.text = "$temperature"
        textView_feels_like_temperature.text = "Feels like $feelsLike"
    }

    private fun updatePrecipitation(volume: Double) {
        textView_precipitation.text = "Precipitation: $volume"
    }

    private fun updateWind(windDirection: String, windSpeed: Double) {
        textView_wind.text = "Wind: $windDirection, $windSpeed"
    }

    private fun updateVisibility(visibilityDistance: Double) {
        textView_visibility.text = "Visibility: $visibilityDistance"
    }
}
