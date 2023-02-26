package com.eimantas_urbonas.weatherapp.util

import com.eimantas_urbonas.weatherapp.data.error.ExceptionType

sealed class Resource<T>(val data: T? = null, val message: ExceptionType? = null) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(data: T? = null, message: ExceptionType) : Resource<T>(data, message)
    class Loading<T>(val isLoading: Boolean = true) : Resource<T>(null)
}
