package com.example.kotlinbaseboilerplate.data

import com.example.kotlinbaseboilerplate.data.network.response.CurrentWeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "4f2b6c8c8fc9285a5cc0f3ed86a9097c"
const val BASE_URL = "http://api.weatherstack.com/"

//Query URL
//http://api.weatherstack.com/current?access_key=4f2b6c8c8fc9285a5cc0f3ed86a9097c&query=London

/**
 * TODO: IMPORTANT! This code varies from the original tutorial since the weather service used
 * TODO: is no longer available!!!
 */
interface WeatherStackApiService {

    //Since we're using coroutines, the return will be a Deferred of the response object
    @GET("current")
    fun getCurrentWeather(
        @Query("query") location: String,
        @Query("units") units: String = "m"
    ): Deferred<CurrentWeatherResponse>

    companion object {
        //It's not necessary an operator function, but since it's a syntactic nicety, let's leave it
        operator fun invoke(): WeatherStackApiService {
            //TODO: since every single request needs to send the "access_value" key for auth,
            //TODO: we create an Interceptor for injecting said value to the request
            val requestInterceptor = Interceptor { chain ->

                //Inject the access_key value to the request url
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("access_key", API_KEY)
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
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build()

            //Finally, we create the retrofit client with the previous interceptor,
            //a adapter factory and a converter factory, associated to the current interface
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherStackApiService::class.java)
        }
    }
}