package com.example.kotlinbaseboilerplate.data.network.weatherbit.response


import com.google.gson.annotations.SerializedName

data class ApiErrorResponse(
    @SerializedName("error")
    val bitError: String
)