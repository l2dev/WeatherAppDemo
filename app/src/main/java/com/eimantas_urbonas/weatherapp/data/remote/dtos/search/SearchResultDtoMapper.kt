package com.eimantas_urbonas.weatherapp.data.remote.dtos.search

import com.eimantas_urbonas.weatherapp.domain.models.SearchResult

fun SearchResultDto.toSearchResult(): SearchResult {
    return SearchResult(
        cityName = cityName,
        region = region,
        latitude = latitude,
        longitude = longitude
    )
}