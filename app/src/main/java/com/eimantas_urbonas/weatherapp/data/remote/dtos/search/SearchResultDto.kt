package com.eimantas_urbonas.weatherapp.data.remote.dtos.search

import com.google.gson.annotations.SerializedName

data class SearchResultDto(
    @SerializedName("name") val cityName: String,
    @SerializedName("region") val region: String,
    @SerializedName("lat") val latitude: Double,
    @SerializedName("lon") val longitude: Double
)