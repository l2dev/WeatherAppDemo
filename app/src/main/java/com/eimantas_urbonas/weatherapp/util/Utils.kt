package com.eimantas_urbonas.weatherapp.util

import android.app.Activity
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.core.view.WindowInsetsCompat
import com.eimantas_urbonas.weatherapp.R
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.roundToInt

object Utils {
    fun String.toDateFormat(timeZone: String): Date {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone(timeZone)
        return dateFormat.parse(this) ?: Date()
    }

    fun String.formatCdnUrl(): String {
        return "https:$this".replaceFirst("http:", "https:")
    }

    fun Double.formatWindSpeed(): String {
        return roundToInt().toString() + " mph"
    }

    fun Int.formatHumidity(): String {
        return DecimalFormat("##%").format(this.toFloat() / 100)
    }

    fun Date.formatDate(): String {
        val formatter = SimpleDateFormat("EEEE, dd MMM yyyy", Locale.getDefault())
        return formatter.format(this)
    }

    fun formatTemperatureRange(
        minTemperature: String,
        maxTemperature: String,
        unit: String
    ): String {
        return "${minTemperature.toFloat()}$unit / ${maxTemperature.toFloat()}$unit"
    }

    fun formatTime(date: Date, timeZone: String): String {
        val zoneId = ZoneId.of(timeZone)
        val zonedDateTime = ZonedDateTime.ofInstant(date.toInstant(), zoneId)
        val formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.getDefault())
        return zonedDateTime.format(formatter).uppercase()
    }

    fun getDayOfWeek(date: Date, timeZone: String): String {
        val zoneId = ZoneId.of(timeZone)
        val zonedDateTime = ZonedDateTime.ofInstant(date.toInstant(), zoneId)
        val today = ZonedDateTime.now(zoneId).dayOfYear
        val dayOfWeek = when (zonedDateTime.dayOfYear) {
            today -> "Today"
            today + 1 -> "Tomorrow"
            else -> zonedDateTime.format(DateTimeFormatter.ofPattern("EEEE"))
        }
        return dayOfWeek
    }

    fun Activity.hideSystemBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Use new system bars API
            window?.insetsController?.hide(WindowInsetsCompat.Type.statusBars())
        } else {
            // Use old system bars API
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        }
    }

    fun Activity.showLocationPermissionError() {
        Toast.makeText(
            this,
            getString(R.string.location_permission_is_required_to_use_this_app),
            Toast.LENGTH_LONG
        ).show()
    }
}