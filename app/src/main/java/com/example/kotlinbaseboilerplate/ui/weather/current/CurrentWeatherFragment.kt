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
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CurrentWeatherFragment : Fragment(), KodeinAware {

    //We need to make the fragment aware to KodeinAware in order to use DI
    //we instantiate kodein by using the closestKodein(), in this case, the one in ForecastApplication
    //You could use local kodein in fragments, etc, if you want to override specifics
    override val kodein by closestKodein()
    private val viewModelFactory: CurrentWeatherViewModelFactory by instance()

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
