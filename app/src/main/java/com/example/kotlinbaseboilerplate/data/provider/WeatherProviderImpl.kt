package com.example.kotlinbaseboilerplate.data.provider

import android.content.Context

private const val KEY_SAVED_AT = "key_saved_at"

class WeatherProviderImpl(
    context: Context
) : PreferenceProvider(context),
    WeatherProvider {

    private val appContext = context.applicationContext

    override fun saveLastSavedAt(savedAt: String) {
        preferences.edit().putString(
            KEY_SAVED_AT,
            savedAt
        ).apply()
    }

    override fun getLastSavedAt(): String? {
        return preferences.getString(KEY_SAVED_AT, null)
    }
}