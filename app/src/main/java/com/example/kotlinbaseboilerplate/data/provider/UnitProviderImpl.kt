package com.example.kotlinbaseboilerplate.data.provider

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.kotlinbaseboilerplate.internal.UnitSystem

const val UNIT_SYSTEM = "UNIT_SYSTEM"


class UnitProviderImpl(context: Context) : UnitProvider {

    private val appContext = context.applicationContext

    private val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    override fun getUnitSystem(): UnitSystem {
        val selectedNme = preferences.getString(UNIT_SYSTEM, UnitSystem.METRIC.name)
        return UnitSystem.valueOf(selectedNme!!)
    }
}