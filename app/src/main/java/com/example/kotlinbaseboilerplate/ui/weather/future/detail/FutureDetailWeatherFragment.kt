package com.example.kotlinbaseboilerplate.ui.weather.future.detail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import com.example.kotlinbaseboilerplate.R
import com.example.kotlinbaseboilerplate.internal.DateNotFoundException
import com.example.kotlinbaseboilerplate.internal.glide.GlideApp
import com.example.kotlinbaseboilerplate.ui.base.ScopedFragment
import com.example.kotlinbaseboilerplate.utils.LocalDateConverter
import kotlinx.android.synthetic.main.future_detail_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.factory
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

class FutureDetailWeatherFragment : ScopedFragment(), KodeinAware {

    //Lazy load DI for this fragment
    override val kodein by closestKodein()

    //TODO: IMPORTANT!!! READ!!!
    //We need to pass the String "date" attribute for getting the weather detail data, so we create
    //a high-order factory, which in turn will create the viewmodel factory and pass the data to it
    //tl;dr we create a higher-order function to get the parametrized data and pass it to the
    //ViewModel factory so we can get the viewmodel instance
    private val viewModelFactoryInstanceFactory
            : ((String) -> FutureDetailWeatherViewModelFactory) by factory()

    private lateinit var viewModel: FutureDetailWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_detail_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //In order to get the data passed to the fragment, we get the arguments passed to the
        //fragment bundle using let{}
        //translation: if the the fragment arguments are not null, get them from the bundle
        val safeArgs = arguments?.let {
            FutureDetailWeatherFragmentArgs.fromBundle(it)
        }

        //We get the date from the safeArgs. This "dateString" argument is the same that we set
        //on the mobile_navigation.xml, and will contain the data passed from the FutureListWeatherFragment
        //If there is no date, or if it's null, throw exception
        val date = safeArgs?.dateString ?: throw DateNotFoundException()

        viewModel = ViewModelProviders.of(this, viewModelFactoryInstanceFactory(date))
            .get(FutureDetailWeatherViewModel::class.java)
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        val futureWeather = viewModel.weather.await()
        val weatherLocation = viewModel.weatherLocation.await()

        weatherLocation.observe(this@FutureDetailWeatherFragment, Observer { location ->
            if (location == null) return@Observer
            updateLocation(location.bitCityName)
        })

        futureWeather.observe(this@FutureDetailWeatherFragment, Observer { weatherEntry ->
            if (weatherEntry == null) return@Observer

            updateDate(weatherEntry.bitDatetime)
            updateTemperatures(weatherEntry.bitTemp,
                weatherEntry.bitMinTemp, weatherEntry.bitMaxTemp)
            updateCondition(weatherEntry.bitWeather.bitDescription)
            updatePrecipitation(weatherEntry.bitPrecip)
            updateWindSpeed(weatherEntry.bitWindSpd)
            updateVisibility(weatherEntry.bitVis)
            updateUv(weatherEntry.bitUv)

            val weatherIcon = weatherEntry.bitWeather.bitIcon+".png"
            val weatherIconUrl = "https://www.weatherbit.io/static/img/icons/$weatherIcon"
            GlideApp.with(this@FutureDetailWeatherFragment)
                .load(weatherIconUrl)
                .into(imageView_condition_icon)
        })
    }

    private fun chooseLocalizedUnitAbbreviation(metric: String, imperial: String): String {
        return if (viewModel.isMetricUnit) metric else imperial
    }

    private fun updateLocation(location: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDate(date: String) {
        val dateObj = LocalDateConverter.stringToDate(date)
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle =
            dateObj?.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
    }

    private fun updateTemperatures(temperature: Double, min: Double, max: Double) {
        textView_temperature.text = "$temperature"
        textView_min_max_temperature.text = "Min: $min, Max: $max"
    }

    private fun updateCondition(condition: String) {
        textView_condition.text = condition
    }

    private fun updatePrecipitation(precipitationVolume: Double) {
        textView_precipitation.text = "Precipitation: $precipitationVolume"
    }

    private fun updateWindSpeed(windSpeed: Double) {
        textView_wind.text = "Wind speed: $windSpeed"
    }

    private fun updateVisibility(visibilityDistance: Double) {
        textView_visibility.text = "Visibility: $visibilityDistance"
    }

    private fun updateUv(uv: Double) {
        textView_uv.text = "UV: $uv"
    }
}
