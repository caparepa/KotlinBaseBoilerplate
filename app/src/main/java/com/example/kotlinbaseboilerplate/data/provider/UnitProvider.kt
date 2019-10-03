package com.example.kotlinbaseboilerplate.data.provider

import com.example.kotlinbaseboilerplate.internal.UnitSystem

interface UnitProvider {
    fun getUnitSystem() : UnitSystem
}