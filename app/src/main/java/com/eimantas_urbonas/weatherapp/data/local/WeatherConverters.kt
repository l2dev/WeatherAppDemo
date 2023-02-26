package com.eimantas_urbonas.weatherapp.data.local

import androidx.room.TypeConverter
import com.eimantas_urbonas.weatherapp.data.local.entities.CurrentWeatherData
import com.eimantas_urbonas.weatherapp.data.local.entities.ForecastData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WeatherConverters {
    @TypeConverter
    fun fromCurrentWeather(value: CurrentWeatherData): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toCurrentWeather(value: String): CurrentWeatherData {
        val type = object : TypeToken<CurrentWeatherData>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromForecastList(forecastList: List<ForecastData>): String {
        val gson = Gson()
        val type = object : TypeToken<List<ForecastData>>() {}.type
        return gson.toJson(forecastList, type)
    }

    @TypeConverter
    fun toForecastList(forecastListString: String): List<ForecastData> {
        val gson = Gson()
        val type = object : TypeToken<List<ForecastData>>() {}.type
        return gson.fromJson(forecastListString, type)
    }
}