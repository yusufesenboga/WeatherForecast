package com.agobnese.weatherApp.views

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.agobnese.weatherApp.database.WeatherRoomDatabase
import com.agobnese.weatherApp.model.ForecastContainer
import com.agobnese.weatherApp.repository.ForecastContainerRepository
import kotlinx.coroutines.launch

class ForecastViewModel(private val forecastContainerRepository: ForecastContainerRepository) :
    ViewModel() {

    private val _forecastLiveData = forecastContainerRepository.forecastLiveData
    val forecastLiveData: LiveData<ForecastContainer>
        get() = _forecastLiveData

    init {
        getForecastContainer()
    }

    fun getForecastContainer() {
        viewModelScope.launch {
            forecastContainerRepository.getForecastContainer()
        }
    }
}

class ForecastViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
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