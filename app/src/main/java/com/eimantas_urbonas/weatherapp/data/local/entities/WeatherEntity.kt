package com.eimantas_urbonas.weatherapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class WeatherEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String = UUID.randomUUID().toString(),
    val time: Long,
    val city: String,
    val date: Long,
    val timezoneId: String,
    val currentWeatherData: CurrentWeatherData,
    val forecastDayList: List<ForecastData>
)

data class CurrentWeatherData(
    val conditionIconUrl: String,
    val temperature: Double,
    val conditionText: String,
    val windSpeed: Double,
    val humidity: Int,
)

data class ForecastData(
    val conditionIconUrl: String,
    val minTemperatureF: Double,
    val maxTemperatureF: Double,
    val date: Date,
    val conditionText: String,
    val windSpeed: Double,
    val humidity: Double,
)