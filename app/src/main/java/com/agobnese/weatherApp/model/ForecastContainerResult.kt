package com.agobnese.weatherApp.model

sealed class ForecastContainerResult {
    class Success(val forecastContainer: ForecastContainer):ForecastContainerResult()
    class Failure(val error: Error):ForecastContainerResult()
    object isLoading : ForecastContainerResult()
}
