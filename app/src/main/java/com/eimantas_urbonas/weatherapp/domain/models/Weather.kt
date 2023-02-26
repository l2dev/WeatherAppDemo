package com.eimantas_urbonas.weatherapp.domain.models

import java.util.*

data class Weather(
    val time: Long,
    val city: String,
    val date: Long,
    val timezoneId: String,
    val currentWeather: CurrentWeather,
    val forecastDayList: List<Forecast>
)

data class CurrentWeather(
    val conditionIconUrl: String,
    val temperature: Double,
    val conditionText: String,
    val windSpeed: Double,
    val humidity: Int,
)

data class Forecast(
    val conditionIconUrl: String,
    val minTemperatureF: Double,
    val maxTemperatureF: Double,
    val date: Date,
    val conditionText: String,
    val windSpeed: Double,
    val humidity: Double,
)