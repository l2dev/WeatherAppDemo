package com.eimantas_urbonas.weatherapp.data.repositories

import com.eimantas_urbonas.weatherapp.data.error.ExceptionType
import com.eimantas_urbonas.weatherapp.data.remote.WeatherApi
import com.eimantas_urbonas.weatherapp.data.remote.dtos.search.toSearchResult
import com.eimantas_urbonas.weatherapp.domain.models.SearchResult
import com.eimantas_urbonas.weatherapp.domain.repositories.SearchRepository
import com.eimantas_urbonas.weatherapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
) : SearchRepository {

    override fun getSearchResults(
        query: String
    ): Flow<Resource<List<SearchResult>>> {
        return flow {

            emit(Resource.Loading(true))

            val searchResults = try {
                api.fetchSearchResults(query).map { it.toSearchResult() }
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = ExceptionType.NETWORK_ERROR))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = ExceptionType.NETWORK_ERROR))
                return@flow
            }

            searchResults.let {
                emit(Resource.Loading(false))
                emit(Resource.Success(searchResults))
            }
        }
    }
}