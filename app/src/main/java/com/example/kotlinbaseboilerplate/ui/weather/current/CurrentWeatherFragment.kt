package com.example.kotlinbaseboilerplate.ui.weather.current

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.example.kotlinbaseboilerplate.R
import com.example.kotlinbaseboilerplate.data.WeatherStackApiService
import com.example.kotlinbaseboilerplate.data.network.ConnectivityInterceptorImpl
import com.example.kotlinbaseboilerplate.data.network.WeatherNetworkDataSourceImpl
import com.example.kotlinbaseboilerplate.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
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
    private fun bindUI() = launch{
        //we fetch the data from the viewmodel
        val currentWeather = viewModel.weather.await()
        //we observe the livedata within the fragment lifecycle, and in the observer we set the
        //ui interaction
        currentWeather.observe(this@CurrentWeatherFragment, Observer{
            if(it == null) return@Observer //if there is no data, return the observer until there is data!
            text_view.text = it.toString()
        })
    }

}
