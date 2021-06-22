package com.agobnese.weatherApp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.agobnese.weatherApp.model.ForecastContainer

@Dao
interface ForecastContainerDao {
    @Query("SELECT * FROM forecastContainerTable LIMIT 1")
    fun getForecastContainer(): ForecastContainer?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(forecastContainer: ForecastContainer)

    @Query("DELETE FROM forecastContainerTable")
    fun deleteAll()
}
