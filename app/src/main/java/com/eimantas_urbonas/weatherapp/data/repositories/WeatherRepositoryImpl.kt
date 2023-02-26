package com.eimantas_urbonas.weatherapp.data.repositories

import com.eimantas_urbonas.weatherapp.data.error.ExceptionType
import com.eimantas_urbonas.weatherapp.data.local.WeatherDatabase
import com.eimantas_urbonas.weatherapp.data.local.entities.toWeather
import com.eimantas_urbonas.weatherapp.data.remote.WeatherApi
import com.eimantas_urbonas.weatherapp.data.remote.dtos.weather.toWeather
import com.eimantas_urbonas.weatherapp.domain.models.Weather
import com.eimantas_urbonas.weatherapp.domain.models.toWeatherEntity
import com.eimantas_urbonas.weatherapp.domain.repositories.WeatherRepository
import com.eimantas_urbonas.weatherapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
    db: WeatherDatabase,
) : WeatherRepository {

    private val dao = db.dao

    override fun getWeather(
        requestForceRefresh: Boolean,
        location: String
    ): Flow<Resource<Weather>> {
        return flow {

            emit(Resource.Loading(true))

            val weatherData = dao.selectWeatherData()

            weatherData?.let {
                emit(
                    Resource.Success(
                        data = weatherData.toWeather()
                    )
                )

                val shouldJustLoadFromCache = !requestForceRefresh

                if (shouldJustLoadFromCache) {
                    emit(Resource.Loading(false))
                    return@flow
                }
            }

            val weather = try {
                api.fetchForecast(location).toWeather()
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = ExceptionType.NETWORK_ERROR))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = ExceptionType.NETWORK_ERROR))
                null
            }

            weather?.let {
                dao.clearWeatherData()
                dao.insertWeatherData(it.toWeatherEntity())
                emit(
                    Resource.Success(
                        data = dao.selectWeatherData()?.toWeather()
                    )
                )
                emit(Resource.Loading(false))
            }
        }
    }
}