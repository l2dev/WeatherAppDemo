package com.eimantas_urbonas.weatherapp.presentation.weather_home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.eimantas_urbonas.weatherapp.R
import com.eimantas_urbonas.weatherapp.ui.theme.PaleBlue
import com.eimantas_urbonas.weatherapp.ui.theme.TransparentDeepBlue
import com.eimantas_urbonas.weatherapp.util.UiText
import com.eimantas_urbonas.weatherapp.util.Utils
import com.eimantas_urbonas.weatherapp.util.Utils.formatCdnUrl
import com.eimantas_urbonas.weatherapp.util.Utils.formatDate
import com.eimantas_urbonas.weatherapp.util.Utils.formatHumidity
import com.eimantas_urbonas.weatherapp.util.Utils.formatTemperatureRange
import com.eimantas_urbonas.weatherapp.util.Utils.formatTime
import com.eimantas_urbonas.weatherapp.util.Utils.formatWindSpeed
import java.util.*

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = hiltViewModel(),
) {
    val state = viewModel.state
    val searchFocusManager = remember { FocusRequester() }
    var searchActive by remember { mutableStateOf(false) }
    val onSearchStateChanged: (Boolean) -> Unit = { searchActive = it }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // background
        BoxWithConstraints {
            Image(
                painter = painterResource(R.drawable.background),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            // gradient
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                TransparentDeepBlue,
                                PaleBlue
                            ),
                            startY = 0.0f,
                            endY = constraints.maxHeight.toFloat()
                        )
                    )
            )
        }

        // search popup
        if (searchActive) {
            LaunchedEffect(Unit) {
                searchFocusManager.requestFocus()
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .zIndex(2f) // Ensure Search appears on top of other composables
            ) {
                SearchBox(
                    isSearching = searchActive,
                    onSearchStateChanged = onSearchStateChanged,
                    searchBoxFocusManager = searchFocusManager,
                )
            }
        }

        // content
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            state.weather?.let { weatherState ->

                // time label and search icon
                ConstraintLayout(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val (timeText, searchIcon) = createRefs()
                    Text(
                        text = formatTime(
                            Date(weatherState.time * 1000),
                            weatherState.timezoneId
                        ),
                        modifier = Modifier.constrainAs(timeText) {
                            top.linkTo(parent.top, margin = 43.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        },
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        style = MaterialTheme.typography.body1
                    )

                    IconButton(
                        onClick = { searchActive = !searchActive },
                        modifier = Modifier
                            .constrainAs(searchIcon) {
                                top.linkTo(timeText.top)
                                bottom.linkTo(timeText.bottom)
                                end.linkTo(parent.end, margin = 37.dp)
                            }
                            .size(24.dp)
                    ) {
                        Icon(
                            tint = Color.White,
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search Icon"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(66.dp))

                // city label
                Text(
                    text = weatherState.city,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h2,
                    color = Color.White,
                )

                Spacer(modifier = Modifier.height(4.dp))

                // current date label
                Text(
                    text = Date(weatherState.time * 1000).formatDate(),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body1,
                    color = Color.White,
                )

                Spacer(modifier = Modifier.height(55.dp))

                // current weather icon
                AsyncImage(
                    modifier = Modifier
                        .height(90.dp)
                        .width(90.dp)
                        .align(Alignment.CenterHorizontally),
                    model = weatherState.currentWeather.conditionIconUrl.formatCdnUrl(),
                    contentDescription = "Current Weather Icon",
                    contentScale = ContentScale.Crop,
                )

                Spacer(modifier = Modifier.height(23.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // current temperature label
                    Text(
                        buildAnnotatedString {
                            withStyle(MaterialTheme.typography.h3.toSpanStyle()) {
                                append(weatherState.currentWeather.temperature.toString())
                            }
                            withStyle(MaterialTheme.typography.h5.toSpanStyle()) {
                                append("°F")
                            }
                        },
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(13.dp))

                // current weather conditions label
                Text(
                    text = weatherState.currentWeather.conditionText,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body1,
                    color = Color.White,
                )

                Spacer(modifier = Modifier.height(21.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {

                    // current wind label
                    Icon(
                        painter = painterResource(id = R.drawable.ic_wind),
                        contentDescription = "Wind icon",
                        tint = Color.White
                    )
                    Text(
                        text = weatherState.currentWeather.windSpeed.formatWindSpeed(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body2,
                        color = Color.White,
                        modifier = Modifier.padding(start = 8.dp)
                    )

                    Spacer(modifier = Modifier.width(40.dp))

                    //current humidity label
                    Icon(
                        painter = painterResource(R.drawable.ic_droplet),
                        contentDescription = "Droplet Icon",
                        tint = Color.White
                    )
                    Text(
                        text = weatherState.currentWeather.humidity.formatHumidity(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body2,
                        color = Color.White,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(70.dp))

                // forecasts (tomorrow, today and friday)
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    itemsIndexed(state.weather.forecastDayList) { index, _ ->
                        Row(Modifier.padding(8.dp)) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(5.dp))
                            ) {

                                val forecastItem = state.weather.forecastDayList[index]

                                AsyncImage(
                                    modifier = Modifier
                                        .height(50.dp)
                                        .width(50.dp),
                                    model = forecastItem.conditionIconUrl.formatCdnUrl(),
                                    contentDescription = "Forecast Weather Icon",
                                    contentScale = ContentScale.Crop,
                                )

                                Text(
                                    text = formatTemperatureRange(
                                        forecastItem.minTemperatureF.toString(),
                                        forecastItem.maxTemperatureF.toString(),
                                        "°F"
                                    ),
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.body2,
                                    color = Color.White,
                                )

                                Text(
                                    text = Utils.getDayOfWeek(
                                        forecastItem.date,
                                        state.weather.timezoneId
                                    ),
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.h4,
                                    color = Color.White,
                                )

                            }
                        }
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            state.isLoading && state.weather == null -> {
                CircularProgressIndicator(
                    color = Color.White
                )
            }
            state.weather == null -> {
                when (state.error) {
                    null -> {
                        // Do nothing when there is no error
                    }
                    else -> {
                        Text(
                            text = UiText.StringResource(state.error.message).asString(),
                            style = MaterialTheme.typography.h1,
                            color = Color.White,
                        )
                    }
                }
            }
        }
    }
}