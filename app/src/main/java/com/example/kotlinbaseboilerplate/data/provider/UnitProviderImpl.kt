package com.example.kotlinbaseboilerplate.data.provider

import android.content.Context
import com.example.kotlinbaseboilerplate.internal.UnitSystem

const val UNIT_SYSTEM = "UNIT_SYSTEM"

class UnitProviderImpl(context: Context) : PreferenceProvider(context),
    UnitProvider {

    override fun getUnitSystem(): UnitSystem {
        val selectedNme = preferences.getString(UNIT_SYSTEM, UnitSystem.METRIC.name)
        return UnitSystem.valueOf(selectedNme!!)
    }
}