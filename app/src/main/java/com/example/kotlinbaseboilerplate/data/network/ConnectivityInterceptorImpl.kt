package com.example.kotlinbaseboilerplate.data.network

import android.content.Context
import android.net.ConnectivityManager
import com.example.kotlinbaseboilerplate.internal.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ConnectivityInterceptorImpl(context: Context) : ConnectivityInterceptor {

    private val appContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isOnline())
            throw NoConnectivityException() //Custom exception created for this interceptor
        return chain.proceed(chain.request())
    }

    //Helper function to check from system services whether there is networkconnection
    private fun isOnline(): Boolean {
        //We create a connectivity manager from system services and cast it as ConnectivityManager
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo //We get network info
        return networkInfo != null && networkInfo.isConnected
    }
}