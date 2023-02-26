package com.eimantas_urbonas.weatherapp.di

import android.app.Application
import androidx.room.Room
import com.eimantas_urbonas.weatherapp.data.local.WeatherDatabase
import com.eimantas_urbonas.weatherapp.data.remote.WeatherApi
import com.eimantas_urbonas.weatherapp.device.LocationHelper
import com.eimantas_urbonas.weatherapp.device.LocationHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(WeatherApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }).build()
            )
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideWeatherDatabase(app: Application): WeatherDatabase {
        return Room.databaseBuilder(
            app,
            WeatherDatabase::class.java,
            "weather.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideLocationHelper(locationHelperImpl: LocationHelperImpl): LocationHelper {
        return locationHelperImpl
    }
}