package com.agobnese.weatherApp.network

import com.agobnese.weatherApp.model.ForecastContainer
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastNetworkService {
    //https://api.weatherbit.io/v2.0/forecast/daily?days=16&postal_code=11235&key=17eb7e05e1a14174850c2c5e6392fcfb
    //https://api.weatherbit.io/v2.0/forecast/daily?days=16&units=M&postal_code=11235&key=17eb7e05e1a14174850c2c5e6392fcfb

    //    ?days=16&postal_code=11235&key=17eb7e05e1a14174850c2c5e6392fcfb

    @GET("daily")
    fun getForecast(
        @Query("days") days: String,
        @Query("units") units: String,
        @Query("postal_code") postal_code: String,
        @Query("key") key: String
    ): Call<ForecastContainer>

}