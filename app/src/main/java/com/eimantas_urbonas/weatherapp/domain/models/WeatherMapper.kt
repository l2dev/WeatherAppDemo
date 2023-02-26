package com.eimantas_urbonas.weatherapp.domain.models

import com.eimantas_urbonas.weatherapp.data.local.entities.CurrentWeatherData
import com.eimantas_urbonas.weatherapp.data.local.entities.ForecastData
import com.eimantas_urbonas.weatherapp.data.local.entities.WeatherEntity

fun Weather.toWeatherEntity(): WeatherEntity {
    return WeatherEntity(
        time = this.time,
        city = this.city,
        date = this.date,
        timezoneId = this.timezoneId,
        currentWeatherData = CurrentWeatherData(
            conditionIconUrl = this.currentWeather.conditionIconUrl,
            temperature = this.currentWeather.temperature,
            conditionText = this.currentWeather.conditionText,
            windSpeed = this.currentWeather.windSpeed,
            humidity = this.currentWeather.humidity
        ),
        forecastDayList = forecastDayList.map {
            ForecastData(
                conditionIconUrl = it.conditionIconUrl,
                minTemperatureF = it.minTemperatureF,
                maxTemperatureF = it.maxTemperatureF,
                date = it.date,
                conditionText = it.conditionText,
                windSpeed = it.windSpeed,
                humidity = it.humidity
            )
        }
    )
}