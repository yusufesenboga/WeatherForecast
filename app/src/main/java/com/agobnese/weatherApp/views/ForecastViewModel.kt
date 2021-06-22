package com.agobnese.weatherApp.views

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.agobnese.weatherApp.database.WeatherRoomDatabase
import com.agobnese.weatherApp.model.ForecastContainerResult
import com.agobnese.weatherApp.repository.ForecastContainerRepository
import kotlinx.coroutines.launch

class ForecastViewModel(private val forecastContainerRepository: ForecastContainerRepository) :
    ViewModel() {

    private val _forecastContainerResultLiveData = forecastContainerRepository.forecastContainerResultLiveData
    val forecastContainerResultLiveData: LiveData<ForecastContainerResult>
        get() = _forecastContainerResultLiveData

    init {
        Log.d("ApplicationTag","init")
        fetchForecastContainer()
    }

    fun getSavedForecastContainer() {
        viewModelScope.launch {
            forecastContainerRepository.getSavedForecastContainer()
        }
    }

    fun fetchForecastContainer() {
        _forecastContainerResultLiveData.value = ForecastContainerResult.IsLoading
        viewModelScope.launch {
            forecastContainerRepository.fetchForecastContainer()
        }
    }
}

class ForecastViewModelFactory(private val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ForecastViewModel::class.java)) {

            val dao = WeatherRoomDatabase.getDatabase(application).getForecastContainerDao()
            val repository = ForecastContainerRepository(dao)
            @Suppress("UNCHECKED_CAST")
            return ForecastViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
