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
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CurrentWeatherFragment : Fragment() {

    companion object {
        fun newInstance() =
            CurrentWeatherFragment()
    }

    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CurrentWeatherViewModel::class.java)

        // TODO: Use the ViewModel

        val apiService = WeatherStackApiService(ConnectivityInterceptorImpl(this.context!!))

        //TODO: Testint the network abstraction! DON'T DO THIS! ONLY A DEMO!
        //We implement the network data source
        val weatherNetworkDataSource = WeatherNetworkDataSourceImpl(apiService)

        //We observer the changes and update the corresponding fields
        weatherNetworkDataSource.downloadedCurrentWeather.observe(this, Observer {
            //We set the text_view value with it, but since it is of type CurrentWeatherResponse,
            //which is the object returned by the Observer, we use toString()
            text_view.text = it.toString()
        })

        //FIXME: Testing the coroutines with retrofit! DO NOT DO THIS!!!
        //Instead of calling the weather api service directly, we call the network impl.
        GlobalScope.launch(Dispatchers.Main) {
            weatherNetworkDataSource.fetchCurrentWeather("London", "m")
        }

    }

}
