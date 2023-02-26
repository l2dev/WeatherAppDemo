package com.eimantas_urbonas.weatherapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.eimantas_urbonas.weatherapp.data.local.entities.WeatherEntity

@Database(
    entities = [WeatherEntity::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(WeatherConverters::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract val dao: WeatherDao
}