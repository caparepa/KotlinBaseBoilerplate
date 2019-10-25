package com.example.kotlinbaseboilerplate.data

import com.example.kotlinbaseboilerplate.BuildConfig
import com.example.kotlinbaseboilerplate.data.network.ConnectivityInterceptor
import com.example.kotlinbaseboilerplate.data.network.weatherbit.response.current.CurrentWeatherResponse
import com.example.kotlinbaseboilerplate.data.network.weatherbit.response.forecast.ForecastWeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//SAMPLE QUERY FOR CURRENT
//http://api.weatherbit.io/v2.0/current?city=caracas&key=API_KEY_HERE&lang=es&units=M

interface WeatherBitApiService {

    @GET("current")
    fun getCurrentWeather(
        @Query("city") city: String,
        @Query("lang") language: String = "en",
        @Query("units") units: String = "M"
    ): Deferred<CurrentWeatherResponse>

    @GET("current")
    fun getCurrentWeatherByLatLon(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("lang") language: String = "en",
        @Query("units") units: String = "M"
    ): Deferred<CurrentWeatherResponse>

    @GET("forecast/daily")
    fun getForecastWeather(
        @Query("city") city: String,
        @Query("lang") language: String = "en",
        @Query("units") units: String = "M"
    ): Deferred<ForecastWeatherResponse>

    @GET("forecast/daily")
    fun getForecastWeatherByLatLong(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("lang") language: String = "en",
        @Query("units") units: String = "M"
    ): Deferred<ForecastWeatherResponse>

    companion object {
        //It's not necessary an operator function, but since it's a syntactic nicety, let's leave it
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): WeatherBitApiService {
            //TODO: since every single request needs to send the "key" key for auth,
            //TODO: we create an Interceptor for injecting said value to the request
            val requestInterceptor = Interceptor { chain ->

                //Inject the access_key value to the request url
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("key", BuildConfig.WEATHERBIT_API_KEY)
                    .build()

                //Build the new url with the previous vlue injection
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                //Proceed to intercept the current request and inject it with the modified url
                return@Interceptor chain.proceed(request)
            }

            //Since we need to intercept every single request made from this api service, we add
            //the interceptor to the HTTP client
            //TODO: We also add an interceptor to check the current network state using DI (WIP)
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            //Finally, we create the retrofit client with the previous interceptor,
            //a adapter factory and a converter factory, associated to the current interface
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BuildConfig.WEATHERBIT_BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherBitApiService::class.java)
        }
    }

}