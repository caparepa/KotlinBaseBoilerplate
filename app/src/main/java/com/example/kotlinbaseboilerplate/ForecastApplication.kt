package com.example.kotlinbaseboilerplate

import android.app.Application
import android.content.Context
import androidx.preference.PreferenceManager
import com.example.kotlinbaseboilerplate.data.WeatherStackApiService
import com.example.kotlinbaseboilerplate.data.db.ForecastDatabase
import com.example.kotlinbaseboilerplate.data.network.ConnectivityInterceptor
import com.example.kotlinbaseboilerplate.data.network.ConnectivityInterceptorImpl
import com.example.kotlinbaseboilerplate.data.network.WeatherNetworkDataSource
import com.example.kotlinbaseboilerplate.data.network.WeatherNetworkDataSourceImpl
import com.example.kotlinbaseboilerplate.data.provider.LocationProvider
import com.example.kotlinbaseboilerplate.data.provider.LocationProviderImpl
import com.example.kotlinbaseboilerplate.data.provider.UnitProvider
import com.example.kotlinbaseboilerplate.data.provider.UnitProviderImpl
import com.example.kotlinbaseboilerplate.data.repository.ForecastRepository
import com.example.kotlinbaseboilerplate.data.repository.ForecastRepositoryImpl
import com.example.kotlinbaseboilerplate.ui.weather.current.CurrentWeatherViewModelFactory
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

/**
 * We create an Application subclass, and make it KodeinAware since we'll be using
 * dependency injecto with Kodein
 */
class ForecastApplication : Application(), KodeinAware {
    //If something is KodeinAware, this val needs to be overridden
    override val kodein = Kodein.lazy {

        //TODO: IMPORTANT! the instance() passed in each binding must be previously bound!

        //We import androidXModule for this specific subclass
        //This provides instances of context and services and anything related to Android
        import(androidXModule((this@ForecastApplication)))

        //We use bind() for the database from a singleton
        //since we don't need two instances of the database, well pass it an instance fetched
        //from the androidXModule, in this case instance() is the applicationContext
        bind() from singleton { ForecastDatabase(instance()) }

        //Now we bind the DAOs using the instance of the previous database binding
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind() from singleton { instance<ForecastDatabase>().currentLocationDao() }

        //We now bind the interceptor interfaces with a singleton that returns its implementation
        //and the instance passed is the applicationContext
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }

        //We bind the api service too, and the instance passed is from the previous binding
        bind() from singleton { WeatherStackApiService(instance()) }

        //We bind the network data source, and the instance passed is from the previous binding
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }

        //We bind the fused location provider
        bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>())}

        //We bind the location provider to pass it to the repository, and we pass instance of the fused location provider and the context
        bind<LocationProvider>() with singleton { LocationProviderImpl(instance(), instance())}

        //We bind the repository, and the instances are from a DAO, provider and the datasource
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance(), instance(), instance()) }
        //bind unit system provider
        bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) }
        //We bind the viewmodel factory, and the instance is the ForecastRepository and UnitProvider
        bind() from provider { CurrentWeatherViewModelFactory(instance(), instance()) }
    }

    override fun onCreate() {
        super.onCreate()

        //We initialize the timezone library used for this project in the application onCreate
        AndroidThreeTen.init(this)
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
    }
}