package com.agobnese.weatherApp.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.agobnese.weatherApp.*

object Prefs {

    lateinit var application: Application

    private val sharedPref: SharedPreferences
        get() = application.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    var unitLetter: String?
        get() = sharedPref.getString(CURRENT_UNIT_DEGREE_KEY, "M")
        set(value) = sharedPref.edit().putString(CURRENT_UNIT_DEGREE_KEY, value).apply()

    var notificationSetting: Boolean
        get() = sharedPref.getBoolean(NOTIFICATION_ENABLE_SETTING_PREF_KEY, false)
        set(value) = sharedPref.edit().putBoolean(NOTIFICATION_ENABLE_SETTING_PREF_KEY, value)
            .apply()

    var dayCount: Int
        get() = sharedPref.getInt(DAY_COUNT_KEY, 0)
        set(value) = sharedPref.edit().putInt(DAY_COUNT_KEY, value).apply()

    var degreeInText: String?
        get() = sharedPref.getString(CURRENT_DEGREE_IN_TEXT, "Celcius")
        set(value) = sharedPref.edit().putString(CURRENT_DEGREE_IN_TEXT, value).apply()

    var currentTimeInMillis: Long
        get() = sharedPref.getLong(TIMESTAMP_KEY, System.currentTimeMillis() - 86400000)
        set(value) = sharedPref.edit().putLong(TIMESTAMP_KEY, value).apply()
}