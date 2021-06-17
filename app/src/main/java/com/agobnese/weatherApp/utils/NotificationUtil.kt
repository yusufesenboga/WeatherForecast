package com.agobnese.weatherApp.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.agobnese.weatherApp.R
import com.agobnese.weatherApp.model.Forecast
import com.agobnese.weatherApp.screen.MainActivity

object NotificationUtil {

    private const val TODAY_FORECAST_CHANNEL_ID = "today_forecast_channel_id"
    private const val TODAY_FORECAST_NOTIFICATION_ID = 1000

    fun fireTodayForecastNotification(context: Context, forecast: Forecast) {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        val contentShort = "Today the weather will be ${forecast.weather.description}. Max temp will be: ${forecast.maxTemp}"
        val contentLong = "Good Morning! Today weather is ${forecast.weather.description}" +
                " expect anywhere from ${forecast.maxTemp} to ${forecast.minTemp} degrees"

        val notification = NotificationCompat.Builder(context, TODAY_FORECAST_CHANNEL_ID)
            .setSmallIcon(R.mipmap.app_logo_round)
            .setContentTitle(context.getString(R.string.today_notification_title))
            .setContentText(contentShort)
            .setStyle(NotificationCompat.BigTextStyle().bigText(contentLong))
            .setSilent(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(TODAY_FORECAST_NOTIFICATION_ID, notification)
    }

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.today_notification_channel_name)
            val descriptionText = context.getString(R.string.today_notification_channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(TODAY_FORECAST_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}