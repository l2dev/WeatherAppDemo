package com.eimantas_urbonas.weatherapp.domain.models

data class SearchResult(
    val cityName: String,
    val region: String,
    val latitude: Double,
    val longitude: Double
)