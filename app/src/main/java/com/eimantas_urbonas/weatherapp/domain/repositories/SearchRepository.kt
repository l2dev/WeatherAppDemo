package com.eimantas_urbonas.weatherapp.domain.repositories

import com.eimantas_urbonas.weatherapp.domain.models.SearchResult
import com.eimantas_urbonas.weatherapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun getSearchResults(
        query: String,
    ): Flow<Resource<List<SearchResult>>>
}