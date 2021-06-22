package com.agobnese.weatherApp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.agobnese.weatherApp.WEATHER_API_KEY
import com.agobnese.weatherApp.database.ForecastContainerDao
import com.agobnese.weatherApp.model.ForecastContainer
import com.agobnese.weatherApp.model.ForecastContainerResult
import com.agobnese.weatherApp.network.ForecastNetworkService
import com.agobnese.weatherApp.network.RetrofitClient
import com.agobnese.weatherApp.utils.Prefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ForecastContainerRepository(private val dao: ForecastContainerDao) {

    val forecastContainerResultLiveData = MutableLiveData<ForecastContainerResult>()

    private fun insertToDatabase(forecastContainer: ForecastContainer) {
        dao.deleteAll()
        dao.insert(forecastContainer)
    }

    suspend fun fetchForecastContainer() {
        withContext(Dispatchers.IO) {
            val unitLetter = Prefs.unitLetter!!
            val lastKnownLocationLat = Prefs.lastKnownLocationLat.toString()
            val lastKnownLocationLon = Prefs.lastKnownLocationLon.toString()

            val client = RetrofitClient.retrofit?.create(ForecastNetworkService::class.java)
            val forecastCall = client?.getForecast(
                "16",
                "38.123",
                "-78.543",
                unitLetter,
                WEATHER_API_KEY
            )

            try {
                Log.d("ApplicationTag", "before execute")
                val response = forecastCall?.execute()
                Log.d("ApplicationTag", "before response")
                val forecastContainer = response?.body()
                forecastContainer?.let {
                    forecastContainerResultLiveData.postValue(ForecastContainerResult.Success(it))
                    Log.d("ApplicationTag", "before inserting database")
                    insertToDatabase(it)
                    Log.d("ApplicationTag", "after inserting database")

                }
            } catch (e: Exception) {
                Log.d("WeatherApplication", e.toString())
                forecastContainerResultLiveData.postValue(
                    ForecastContainerResult.Failure(Error(e.message))
                )
            }
        }
    }

    suspend fun getSavedForecastContainer() {
        withContext(Dispatchers.IO) {
            val forecastContainer = dao.getForecastContainer()
            forecastContainer?.let {
            forecastContainerResultLiveData.postValue(
                ForecastContainerResult.Success(forecastContainer)
            )
            }
        }
    }
}