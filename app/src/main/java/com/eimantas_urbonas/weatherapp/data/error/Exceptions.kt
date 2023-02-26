package com.eimantas_urbonas.weatherapp.data.error

import androidx.annotation.StringRes
import com.eimantas_urbonas.weatherapp.R

enum class ExceptionType(@StringRes val message: Int) {
    NETWORK_ERROR(R.string.error_cannot_load_weather_from_api)
}