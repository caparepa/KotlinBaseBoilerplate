package com.example.kotlinbaseboilerplate.internal

import java.io.IOException

class NoConnectivityException : IOException()

class LocationPermissionNotGrantedException : Exception()

class DateNotFoundException : Exception()

class ApiException(message: String) : IOException(message)
