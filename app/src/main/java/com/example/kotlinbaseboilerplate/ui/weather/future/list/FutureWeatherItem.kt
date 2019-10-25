package com.example.kotlinbaseboilerplate.ui.weather.future.list

import com.example.kotlinbaseboilerplate.R
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.forecast.ForecastWeatherData
import com.example.kotlinbaseboilerplate.internal.glide.GlideApp
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_future_weather.*
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

class FutureWeatherItem(
    val weatherEntry: ForecastWeatherData
): Item() {

    /**
     * NOTE: in this case, instead of using the native ViewHolder library, we use Groupie which is
     * basically a wrapper.
     */

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            textView_condition.text = weatherEntry.bitWeather.bitDescription
            updateDate() //We can call the extension here since it's in the same scope (hence the "apply")
            updateTemperature()
            updateWeatherImage()
        }
    }

    override fun getLayout() = R.layout.item_future_weather

    //We make an extension function on the ViewHolder since we're gonna to work on one of its elements
    private fun ViewHolder.updateDate() {
        val dtFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        textView_date.text = weatherEntry.bitDatetime.format(dtFormatter)
    }

    private fun ViewHolder.updateTemperature() {
        //TODO: do a bunch of logic here to get the units
        val uniAbbreviation = "°C"
        textView_temperature.text = "${weatherEntry.bitTemp}$uniAbbreviation"
    }

    private fun ViewHolder.updateWeatherImage() {
        val weatherIcon = weatherEntry.bitWeather.bitIcon+".png"
        val weatherIconUrl = "https://www.weatherbit.io/static/img/icons/$weatherIcon"
        GlideApp.with(this.containerView)
            .load(weatherIconUrl)
            .into(imageView_condition_icon)
    }
}