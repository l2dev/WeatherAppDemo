package com.eimantas_urbonas.weatherapp.data.local.entities

import com.eimantas_urbonas.weatherapp.domain.models.CurrentWeather
import com.eimantas_urbonas.weatherapp.domain.models.Forecast
import com.eimantas_urbonas.weatherapp.domain.models.Weather

fun WeatherEntity.toWeather(): Weather {
    return Weather(
        time = this.time,
        city = this.city,
        date = this.date,
        timezoneId = this.timezoneId,
        currentWeather = CurrentWeather(
            conditionIconUrl = this.currentWeatherData.conditionIconUrl,
            temperature = this.currentWeatherData.temperature,
            conditionText = this.currentWeatherData.conditionText,
            windSpeed = this.currentWeatherData.windSpeed,
            humidity = this.currentWeatherData.humidity,
        ),
        forecastDayList = forecastDayList.map { forecastData ->
            Forecast(
                conditionIconUrl = forecastData.conditionIconUrl,
                minTemperatureF = forecastData.minTemperatureF,
                maxTemperatureF = forecastData.maxTemperatureF,
                date = forecastData.date,
                conditionText = forecastData.conditionText,
                windSpeed = forecastData.windSpeed,
                humidity = forecastData.humidity,
            )
        }
    )
}