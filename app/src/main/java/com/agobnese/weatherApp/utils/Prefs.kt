package com.agobnese.weatherApp.utils

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.agobnese.weatherApp.*

object Prefs {

    lateinit var application: Application

//    var unitLetter = "M"
//    var dayCountValue = 0

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

    fun setNotificationEnablingSetting(activity: Activity, value: Boolean) {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean(NOTIFICATION_ENABLE_SETTING_PREF_KEY, value)
        editor.apply()
    }

    fun retrieveNotificationEnablingSetting(activity: Activity): Boolean {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        return sharedPref.getBoolean(NOTIFICATION_ENABLE_SETTING_PREF_KEY, false)
    }

    fun setDegreeUnitLetter(activity: Activity, value: String) {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(CURRENT_UNIT_DEGREE_KEY, value)
        editor.apply()
    }

    fun retrieveDegreeUnitLetter(activity: Activity): String? {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        return sharedPref.getString(CURRENT_UNIT_DEGREE_KEY, "M")
    }

    fun setDayCount(activity: Activity, value: Int) {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putInt(DAY_COUNT_KEY, value)
        editor.apply()
    }

    fun retrieveDayCount(activity: Activity): Int {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        return sharedPref.getInt(DAY_COUNT_KEY, 0)
    }
}