package com.example.kotlinbaseboilerplate.data.provider

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

private const val KEY_SAVED_AT = "key_saved_at"

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

    /**
     * TODO: BEWARE THIS!!!!
     */
    fun getLastSavedAt(): String? {
        return preferences.getString(KEY_SAVED_AT, null)
    }

}