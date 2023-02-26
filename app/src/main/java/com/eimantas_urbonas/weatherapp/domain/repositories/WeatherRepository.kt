package com.eimantas_urbonas.weatherapp.domain.repositories

import com.eimantas_urbonas.weatherapp.domain.models.Weather
import com.eimantas_urbonas.weatherapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getWeather(
        requestForceRefresh: Boolean,
        location: String
    ): Flow<Resource<Weather>>
}