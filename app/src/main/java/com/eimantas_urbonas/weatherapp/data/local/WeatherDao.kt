package com.eimantas_urbonas.weatherapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eimantas_urbonas.weatherapp.data.local.entities.WeatherEntity

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weatherentity LIMIT 1")
    suspend fun selectWeatherData(): WeatherEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherData(
        weatherEntity: WeatherEntity,
    )

    @Query("DELETE FROM weatherentity")
    suspend fun clearWeatherData()
}