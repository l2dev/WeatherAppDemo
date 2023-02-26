package com.eimantas_urbonas.weatherapp.presentation.weather_home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SearchBox(
    viewModel: WeatherViewModel = hiltViewModel(),
    isSearching: Boolean,
    onSearchStateChanged: (Boolean) -> Unit,
    searchBoxFocusManager: FocusRequester,
) {
    var query by remember { mutableStateOf("") }

    val state = viewModel.state

    val popupPositionProvider = object : PopupPositionProvider {
        override fun calculatePosition(
            anchorBounds: IntRect,
            windowSize: IntSize,
            layoutDirection: LayoutDirection,
            popupContentSize: IntSize
        ): IntOffset {
            // calculate the position of the dropdown
            val x = anchorBounds.left
            val y = anchorBounds.top + anchorBounds.height
            return IntOffset(x, y)
        }
    }

    // search box layout with rounded bottom corners
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(104.dp)
            .background(
                Color.White,
                if (query.isEmpty() || query.isNotEmpty() && state.searchResults.isEmpty()) {
                    RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                } else {
                    RoundedCornerShape(0.dp)
                }

            )
            .padding(16.dp)
    ) {
        val (backButton, searchBox) = createRefs()

        // search icon
        IconButton(
            onClick = { onSearchStateChanged(!isSearching) },
            modifier = Modifier.constrainAs(backButton) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back Icon"
            )
        }

        val textFieldColors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            focusedIndicatorColor = Color.White,
            unfocusedIndicatorColor = Color.White
        )

        // search box layout
        Box(
            modifier = Modifier
                .constrainAs(searchBox) {
                    start.linkTo(backButton.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end, margin = 16.dp)
                    width = Dimension.fillToConstraints
                }
                .border(1.dp, Color.Gray, RoundedCornerShape(16.dp))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                // search input field
                TextField(
                    placeholder = { Text("Search City") },
                    value = query,
                    onValueChange = {
                        query = it
                        viewModel.onEvent(
                            UiEvent.OnSearchQueryChange(query = query)
                        )
                    },
                    colors = textFieldColors,
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(searchBoxFocusManager),
                )

                if (query.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Clear search text",
                        modifier = Modifier
                            .padding(end = 20.dp)
                            .clickable {
                                query = ""
                            }
                    )
                }
            }
        }

        // dropdown popup
        if (query.isNotEmpty() && state.searchResults.isNotEmpty()) {
            Popup(
                properties = PopupProperties(focusable = false),
                popupPositionProvider = popupPositionProvider
            ) {
                Box(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxWidth()
                        .heightIn(max = 300.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .padding(8.dp)
                    ) {
                        state.searchResults.forEach { result ->
                            DropdownMenuItem(
                                onClick = {
                                    onSearchStateChanged(false)
                                    viewModel.onEvent(
                                        UiEvent.OnGetWeatherData(
                                            location = "${result.latitude},${result.longitude}",
                                            requestForceRefresh = true
                                        )
                                    )
                                }
                            ) {
                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                            append(result.cityName)
                                        }
                                        append(" - ")
                                        append(result.region)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
