package com.eimantas_urbonas.weatherapp.data.remote.dtos.weather

import com.eimantas_urbonas.weatherapp.domain.models.CurrentWeather
import com.eimantas_urbonas.weatherapp.domain.models.Forecast
import com.eimantas_urbonas.weatherapp.domain.models.Weather
import com.eimantas_urbonas.weatherapp.util.Utils.toDateFormat
import java.time.*
import java.util.*

fun WeatherDto.toWeather(): Weather {
    val currentWeather = CurrentWeather(
        conditionIconUrl = currentWeatherData.condition.icon,
        temperature = currentWeatherData.temperatureF,
        conditionText = currentWeatherData.condition.text,
        windSpeed = currentWeatherData.windSpeedMph,
        humidity = currentWeatherData.humidity,
    )

    return Weather(
        time = locationData.localTimeEpoch,
        city = locationData.name,
        date = locationData.localTimeEpoch,
        timezoneId = locationData.timezoneId,
        currentWeather = currentWeather,
        forecastDayList = forecast.forecastDayList.filterForecastDays(
            timeZoneId = locationData.timezoneId,
            maxDays = 3
        ).map { it.day.toForecast(it.date.toDateFormat(locationData.timezoneId)) }
    )
}

private fun WeatherDto.Day.toForecast(date: Date): Forecast {
    return Forecast(
        conditionIconUrl = condition.icon,
        minTemperatureF = minTemperatureF,
        maxTemperatureF = maxTemperatureF,
        date = date,
        conditionText = condition.text,
        windSpeed = maxWindSpeedMph,
        humidity = averageHumidity
    )
}

// Filter forecastDayList to include only today, tomorrow, and the closest Friday
private fun List<WeatherDto.ForecastDay>.filterForecastDays(
    timeZoneId: String,
    maxDays: Int
): List<WeatherDto.ForecastDay> {
    val currentDateTime = ZonedDateTime.now(ZoneId.of(timeZoneId))
    val filteredList = this.filter { forecastDay ->
        val date = LocalDate.parse(forecastDay.date)
        val zonedDateTime = ZonedDateTime.of(date, LocalTime.MIDNIGHT, ZoneId.of(timeZoneId))
        val dayOfWeek = zonedDateTime.dayOfWeek
        dayOfWeek == currentDateTime.dayOfWeek ||
                dayOfWeek == currentDateTime.plusDays(1).dayOfWeek ||
                dayOfWeek == DayOfWeek.FRIDAY
    }.take(maxDays)
    return filteredList
}