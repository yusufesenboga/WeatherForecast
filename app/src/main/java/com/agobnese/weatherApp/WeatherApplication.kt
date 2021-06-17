package com.agobnese.weatherApp

import android.app.Application
import com.agobnese.weatherApp.utils.NotificationUtil
import com.agobnese.weatherApp.utils.Prefs

class WeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Prefs.application = this
        NotificationUtil.createNotificationChannel(applicationContext)
    }
}