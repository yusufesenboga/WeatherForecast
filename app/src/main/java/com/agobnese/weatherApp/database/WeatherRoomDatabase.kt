package com.agobnese.weatherApp.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.agobnese.weatherApp.WEATHER_ROOM_DATABASE_NAME
import com.agobnese.weatherApp.model.ForecastContainer

@Database(entities = arrayOf(ForecastContainer::class), version = 1)
@TypeConverters(ForecastListConverter::class, WeatherConverter::class)
abstract class WeatherRoomDatabase : RoomDatabase() {
    abstract fun getForecastContainerDao(): ForecastContainerDao

    companion object {
        @Volatile
        private var INSTANCE: WeatherRoomDatabase? = null

        fun getDatabase(application: Application): WeatherRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    application.applicationContext,
                    WeatherRoomDatabase::class.java,
                    WEATHER_ROOM_DATABASE_NAME
                ).fallbackToDestructiveMigration().build()

                INSTANCE = instance
                instance
            }
        }
    }
}