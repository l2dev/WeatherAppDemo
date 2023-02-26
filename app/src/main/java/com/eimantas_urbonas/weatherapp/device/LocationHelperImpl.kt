package com.eimantas_urbonas.weatherapp.device

import android.content.Context
import android.location.Location
import android.location.LocationManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationHelperImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : LocationHelper {

    private val locationManager: LocationManager by lazy {
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    override suspend fun getLocation(): Location? {
        return withContext(Dispatchers.IO) {
            try {
                val locationProvider = LocationManager.NETWORK_PROVIDER
                return@withContext locationManager.getLastKnownLocation(locationProvider)
            } catch (e: SecurityException) {
                null
            }
        }
    }
}