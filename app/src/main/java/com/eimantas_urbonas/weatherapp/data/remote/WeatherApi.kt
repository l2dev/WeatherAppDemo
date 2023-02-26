package com.eimantas_urbonas.weatherapp.data.remote

import com.eimantas_urbonas.weatherapp.data.remote.dtos.search.SearchResultDto
import com.eimantas_urbonas.weatherapp.data.remote.dtos.weather.WeatherDto
import com.eimantas_urbonas.weatherapp.util.Constants
import retrofit2.http.POST
import retrofit2.http.Query

interface WeatherApi {
    @POST("/v1/forecast.json?key=${Constants.WEATHER_API_KEY}&days=10&aqi=no&alerts=no&hour=-1")
    suspend fun fetchForecast(@Query("q") location: String): WeatherDto

    @POST("/v1/search.json?key=${Constants.WEATHER_API_KEY}")
    suspend fun fetchSearchResults(@Query("q") query: String): List<SearchResultDto>

    companion object {
        const val BASE_URL = "https://api.weatherapi.com/"
    }
}
