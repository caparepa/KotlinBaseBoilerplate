package com.example.kotlinbaseboilerplate.data.provider

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.kotlinbaseboilerplate.utils.KEY_SAVED_AT

abstract class PreferenceProvider(context: Context) {

    private val appContext = context.applicationContext

    protected val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    fun saveLastSavedAt(savedAt: String) {
        preferences.edit().putString(
            KEY_SAVED_AT,
            savedAt
        ).apply()
    }

    fun getLastSavedAt(): String? {
        return preferences.getString(KEY_SAVED_AT, null)
    }

}