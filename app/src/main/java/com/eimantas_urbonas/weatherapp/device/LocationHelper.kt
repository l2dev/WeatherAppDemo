package com.eimantas_urbonas.weatherapp.device

import android.location.Location

interface LocationHelper {
     suspend fun getLocation(): Location?
}