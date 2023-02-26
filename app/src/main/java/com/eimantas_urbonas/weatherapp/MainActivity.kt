package com.eimantas_urbonas.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.eimantas_urbonas.weatherapp.presentation.weather_home.WeatherScreen
import com.eimantas_urbonas.weatherapp.ui.theme.WeatherAppTheme
import com.eimantas_urbonas.weatherapp.util.PermissionHelper
import com.eimantas_urbonas.weatherapp.util.Utils.hideSystemBar
import com.eimantas_urbonas.weatherapp.util.Utils.showLocationPermissionError
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val permissionHelper by lazy {
        PermissionHelper(
            activity = this,
            onPermissionsGranted = {
                loadWeatherScreen()
            },
            onPermissionsDenied = {
                loadWeatherScreen()
                showLocationPermissionError()
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionHelper.checkLocationPermission()
    }

    private fun loadWeatherScreen() {
        setContent {
            WeatherAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    WeatherScreen()
                }
            }
        }
        hideSystemBar()
    }
}