package com.eimantas_urbonas.weatherapp.di

import com.eimantas_urbonas.weatherapp.data.repositories.SearchRepositoryImpl
import com.eimantas_urbonas.weatherapp.data.repositories.WeatherRepositoryImpl
import com.eimantas_urbonas.weatherapp.domain.repositories.SearchRepository
import com.eimantas_urbonas.weatherapp.domain.repositories.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        weatherRepository: WeatherRepositoryImpl
    ): WeatherRepository

    @Binds
    @Singleton
    abstract fun bindSearchRepository(
        searchRepository: SearchRepositoryImpl
    ): SearchRepository
}