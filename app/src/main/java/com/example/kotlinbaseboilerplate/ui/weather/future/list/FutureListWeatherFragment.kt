package com.example.kotlinbaseboilerplate.ui.weather.future.list

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.kotlinbaseboilerplate.R
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.forecast.ForecastWeatherData
import com.example.kotlinbaseboilerplate.ui.base.ScopedFragment
import com.example.kotlinbaseboilerplate.utils.LocalDateConverter
import com.example.kotlinbaseboilerplate.utils.makeGone
import com.example.kotlinbaseboilerplate.utils.toastLong
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.future_list_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import org.threeten.bp.LocalDate

class FutureListWeatherFragment : ScopedFragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: FutureListWeatherViewModelFactory by instance()

    private lateinit var viewModel: FutureListWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_list_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(FutureListWeatherViewModel::class.java)
        bindUI()
    }

    /**
     * Bind the UI with coroutines (what's why this fragment extends from ScopedFragment(),
     * and since this function will update the UI, the coroutine will be dispatched on the main
     * thread (hence the Dispatchers.Main)
     */
    private fun bindUI() = launch(Dispatchers.Main) {
        val futureWeatherEntries = viewModel.weatherEntries.await()
        val weatherLocation = viewModel.weatherLocation.await()

        weatherLocation.observe(this@FutureListWeatherFragment, Observer {location ->
            if(location == null) return@Observer
            updateLocation(location.bitCityName)
        })

        futureWeatherEntries.observe(this@FutureListWeatherFragment, Observer { weatherEntries ->
            if(weatherEntries == null) return@Observer

            group_loading.makeGone()

            //TODO: figure out how to update the location with the current data structure!

            updateDateToNextDays()
            initRecyclerView(weatherEntries.toFutureWeatherItems()) //we apply the mapping here
        })

    }

    private fun updateLocation(location : String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDateToNextDays() {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Next 16 days"
    }

    //We map the list of weather data to future weather item
    private fun List<ForecastWeatherData>.toFutureWeatherItems() : List<FutureWeatherItem> {
        return this.map{
            FutureWeatherItem(it)
        }
    }

    private fun initRecyclerView(items: List<FutureWeatherItem>) {
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@FutureListWeatherFragment.context)
            adapter = groupAdapter
        }

        groupAdapter.setOnItemClickListener { item, view ->
            (item as? FutureWeatherItem)?.let {
                showWeatherDetail(it.weatherEntry.bitDatetime, view)
            }
        }
    }

    /**
     * We create a function to navigate from the list fragment to the detail fragment using the
     * Android Navigation components
     */
    private fun showWeatherDetail(date: String, view: View) {
        val actionDetail = FutureListWeatherFragmentDirections
            .actionFutureListWeatherFragmentToFutureDetailWeatherFragment(date)
        Navigation.findNavController(view).navigate(actionDetail)
    }
}
