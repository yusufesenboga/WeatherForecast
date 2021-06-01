package com.agobnese.weatherApp.database

import androidx.room.TypeConverter
import com.agobnese.weatherApp.model.Forecast
import com.agobnese.weatherApp.model.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WeatherConverter {
    @TypeConverter
    fun weatherToString(weather: Weather): String = Gson().toJson(weather)

    @TypeConverter
    fun stringToWeather(string: String): Weather = Gson().fromJson(string, Weather::class.java)
}

class ForecastListConverter {
    @TypeConverter
    fun fromListToString(forecastList: List<Forecast>): String {
        val type = object : TypeToken<List<Forecast>>() {}.type
        return Gson().toJson(forecastList, type)
    }

    @TypeConverter
    fun fromStringToList(string: String): List<Forecast> {
        val type = object : TypeToken<List<Forecast>>() {}.type
        return Gson().fromJson(string, type)
    }

}