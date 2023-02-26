package com.eimantas_urbonas.weatherapp.presentation.weather_home

sealed class UiEvent {
    data class OnGetWeatherData(
        val location: String,
        val requestForceRefresh: Boolean
    ) : UiEvent()

    data class OnSearchQueryChange(
        val query: String
    ) : UiEvent()
}