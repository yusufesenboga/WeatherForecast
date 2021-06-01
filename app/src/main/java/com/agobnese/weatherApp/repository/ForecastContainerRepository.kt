package com.agobnese.weatherApp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.agobnese.weatherApp.WEATHER_API_KEY
import com.agobnese.weatherApp.database.ForecastContainerDao
import com.agobnese.weatherApp.model.ForecastContainer
import com.agobnese.weatherApp.network.ForecastNetworkService
import com.agobnese.weatherApp.network.RetrofitClient
import com.agobnese.weatherApp.utils.Prefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ForecastContainerRepository(private val dao: ForecastContainerDao) {

    val forecastLiveData: LiveData<ForecastContainer> = dao.getForecastContainer()

    private fun insertToDatabase(forecastContainer: ForecastContainer) {
        dao.deleteAll()
        dao.insert(forecastContainer)
    }

    suspend fun getForecastContainer() {
        withContext(Dispatchers.IO) {
            val unitLetter = Prefs.unitLetter
            unitLetter?.let { fetchForecastContainer(it) }
        }
    }

    private fun checkIfInternetIsNeeded(): Boolean {
        return System.currentTimeMillis() >= Prefs.currentTimeInMillis - 1800000
//        return System.currentTimeMillis() >= Prefs.currentTimeInMillis - 30000
    }

    //Backend
    private fun fetchForecastContainer(unitLetter: String) {
        val client = RetrofitClient.retrofit?.create(ForecastNetworkService::class.java)
        val forecastCall = client?.getForecast("16", unitLetter, "11235", WEATHER_API_KEY)

        try {
            if (checkIfInternetIsNeeded()) {
                val response = forecastCall?.execute()
                val forecastContainer = response?.body()
                forecastContainer?.let {
                    insertToDatabase(it)
                }
            }
//            TODO: handle error cases when forecastContainer is null
        } catch (e: Exception) {
            Log.d("WeatherApplication", e.toString())
        }
    }

}