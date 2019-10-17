package com.example.kotlinbaseboilerplate.data.repository.weatherbit

import androidx.lifecycle.LiveData
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.current.BitCurrentWeatherData
import com.example.kotlinbaseboilerplate.data.db.weatherbit.entity.current.BitWeatherDescription

class BitForecastRepositoryImpl : BitForecastRepository {
    override suspend fun getBitCurrentWeatherData(): LiveData<BitCurrentWeatherData> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getBitWeatherDescription(): LiveData<BitWeatherDescription> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}