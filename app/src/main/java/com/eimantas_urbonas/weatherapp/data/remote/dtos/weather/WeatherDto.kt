package com.eimantas_urbonas.weatherapp.data.remote.dtos.weather

import com.google.gson.annotations.SerializedName

data class WeatherDto(
    @SerializedName("location") val locationData: LocationData,
    @SerializedName("current") val currentWeatherData: CurrentWeatherData,
    @SerializedName("forecast") val forecast: Forecast
) {
    data class LocationData(
        @SerializedName("name") val name: String,
        @SerializedName("region") val region: String,
        @SerializedName("country") val country: String,
        @SerializedName("lat") val latitude: Double,
        @SerializedName("lon") val longitude: Double,
        @SerializedName("tz_id") val timezoneId: String,
        @SerializedName("localtime_epoch") val localTimeEpoch: Long,
        @SerializedName("localtime") val localTime: String
    )

    data class CurrentWeatherData(
        @SerializedName("last_updated_epoch") val lastUpdatedEpoch: Long,
        @SerializedName("last_updated") val lastUpdated: String,
        @SerializedName("temp_c") val temperatureC: Double,
        @SerializedName("temp_f") val temperatureF: Double,
        @SerializedName("is_day") val isDay: Int,
        @SerializedName("condition") val condition: Condition,
        @SerializedName("wind_mph") val windSpeedMph: Double,
        @SerializedName("wind_kph") val windSpeedKph: Double,
        @SerializedName("wind_degree") val windDegree: Int,
        @SerializedName("wind_dir") val windDirection: String,
        @SerializedName("pressure_mb") val pressureMb: Double,
        @SerializedName("pressure_in") val pressureIn: Double,
        @SerializedName("precip_mm") val precipitationMm: Double,
        @SerializedName("precip_in") val precipitationIn: Double,
        @SerializedName("humidity") val humidity: Int,
        @SerializedName("cloud") val cloud: Int,
        @SerializedName("feelslike_c") val feelsLikeC: Double,
        @SerializedName("feelslike_f") val feelsLikeF: Double,
        @SerializedName("vis_km") val visibilityKm: Double,
        @SerializedName("vis_miles") val visibilityMiles: Double,
        @SerializedName("uv") val uvIndex: Double,
        @SerializedName("gust_mph") val gustSpeedMph: Double,
        @SerializedName("gust_kph") val gustSpeedKph: Double
    )

    data class Forecast(
        @SerializedName("forecastday") val forecastDayList: List<ForecastDay>
    )

    data class ForecastDay(
        @SerializedName("date") val date: String,
        @SerializedName("date_epoch") val dateEpoch: Long,
        @SerializedName("day") val day: Day,
        @SerializedName("astro") val astro: Astro,
        @SerializedName("hour") val hour: List<Any>
    )

    data class Day(
        @SerializedName("maxtemp_c") val maxTemperatureC: Double,
        @SerializedName("maxtemp_f") val maxTemperatureF: Double,
        @SerializedName("mintemp_c") val minTemperatureC: Double,
        @SerializedName("mintemp_f") val minTemperatureF: Double,
        @SerializedName("avgtemp_c") val averageTemperatureC: Double,
        @SerializedName("avgtemp_f") val averageTemperatureF: Double,
        @SerializedName("maxwind_mph") val maxWindSpeedMph: Double,
        @SerializedName("maxwind_kph") val maxWindSpeedKph: Double,
        @SerializedName("totalprecip_mm") val totalPrecipitationMm: Double,
        @SerializedName("totalprecip_in") val totalPrecipitationIn: Double,
        @SerializedName("totalsnow_cm") val totalSnowCm: Double,
        @SerializedName("avgvis_km") val averageVisibilityKm: Double,
        @SerializedName("avgvis_miles") val averageVisibilityMiles: Double,
        @SerializedName("avghumidity") val averageHumidity: Double,
        @SerializedName("daily_will_it_rain") val willItRain: Int,
        @SerializedName("daily_chance_of_rain") val chanceOfRain: Int,
        @SerializedName("daily_will_it_snow") val willItSnow: Int,
        @SerializedName("daily_chance_of_snow") val chanceOfSnow: Int,
        @SerializedName("condition") val condition: Condition,
        @SerializedName("uv") val uvIndex: Double
    )

    data class Astro(
        @SerializedName("sunrise") val sunrise: String,
        @SerializedName("sunset") val sunset: String,
        @SerializedName("moonrise") val moonrise: String,
        @SerializedName("moonset") val moonset: String,
        @SerializedName("moon_phase") val moonPhase: String,
        @SerializedName("moon_illumination") val moonIllumination: String,
        @SerializedName("is_moon_up") val isMoonUp: Int,
        @SerializedName("is_sun_up") val isSunUp: Int
    )

    data class Condition(
        @SerializedName("text") val text: String,
        @SerializedName("icon") val icon: String,
        @SerializedName("code") val code: Int
    )
}