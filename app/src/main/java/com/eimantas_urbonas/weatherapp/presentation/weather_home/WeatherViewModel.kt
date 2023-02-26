package com.eimantas_urbonas.weatherapp.presentation.weather_home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eimantas_urbonas.weatherapp.device.LocationHelper
import com.eimantas_urbonas.weatherapp.domain.repositories.SearchRepository
import com.eimantas_urbonas.weatherapp.domain.repositories.WeatherRepository
import com.eimantas_urbonas.weatherapp.util.Constants.DEFAULT_LATITUDE
import com.eimantas_urbonas.weatherapp.util.Constants.DEFAULT_LONGITUDE
import com.eimantas_urbonas.weatherapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationHelper: LocationHelper,
    private val searchRepository: SearchRepository,
) : ViewModel() {

    var state by mutableStateOf(WeatherState())

    init {
        viewModelScope.launch {
            val location = locationHelper.getLocation()
            location?.let {
                onLocationReceived("${location.latitude},${location.longitude}")
            } ?: run {
                onLocationReceived("${DEFAULT_LATITUDE},${DEFAULT_LONGITUDE}")
            }
        }
    }

    fun onEvent(uiEvent: UiEvent) {
        when (uiEvent) {
            is UiEvent.OnGetWeatherData -> {
                getWeatherData(
                    location = uiEvent.location,
                    requestForceRefresh = uiEvent.requestForceRefresh,
                )
            }
            is UiEvent.OnSearchQueryChange -> {
                getSearchResults(query = uiEvent.query)
            }
        }
    }

    private fun getWeatherData(
        location: String = "",
        requestForceRefresh: Boolean = false,
    ) {
        viewModelScope.launch {
            weatherRepository
                .getWeather(requestForceRefresh, location)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { weather ->
                                state = state.copy(
                                    weather = weather
                                )
                            }
                        }
                        is Resource.Error -> {
                            state = state.copy(
                                isLoading = false,
                                error = result.message,
                            )
                        }
                        is Resource.Loading -> {
                            state = state.copy(isLoading = result.isLoading)
                        }
                    }
                }

        }
    }

    private fun getSearchResults(
        query: String = state.searchQuery.lowercase(),
    ) {
        viewModelScope.launch {
            searchRepository
                .getSearchResults(query)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { searchResults ->
                                state = state.copy(
                                    searchResults = searchResults
                                )
                            }
                        }
                        is Resource.Error -> {
                            state = state.copy(
                                isLoading = false,
                                error = result.message,
                            )
                        }
                        is Resource.Loading -> {
                            state = state.copy(isLoading = result.isLoading)
                        }
                    }
                }
        }
    }

    private fun onLocationReceived(location: String) {
        getWeatherData(
            location = location,
            requestForceRefresh = true
        )
    }
}