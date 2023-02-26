package com.eimantas_urbonas.weatherapp.presentation.weather_home

import com.eimantas_urbonas.weatherapp.data.error.ExceptionType
import com.eimantas_urbonas.weatherapp.domain.models.SearchResult
import com.eimantas_urbonas.weatherapp.domain.models.Weather

data class WeatherState(
    val weather: Weather? = null,
    val isLoading: Boolean = false,
    val error: ExceptionType? = null,
    val searchQuery: String = "",
    val searchResults: List<SearchResult> = emptyList(),
)