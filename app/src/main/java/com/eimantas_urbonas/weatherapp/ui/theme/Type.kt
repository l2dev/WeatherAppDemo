package com.eimantas_urbonas.weatherapp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.eimantas_urbonas.weatherapp.R

val SfPro = FontFamily(
    Font(R.font.sf_pro_regular),
    Font(R.font.sf_pro_bold, FontWeight.Bold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    h1 = TextStyle(
        fontFamily = SfPro,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    h2 = TextStyle(
        fontFamily = SfPro,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    ),
    h3 = TextStyle(
        fontFamily = SfPro,
        fontWeight = FontWeight.Bold,
        fontSize = 56.sp
    ),
    h4 = TextStyle(
        fontFamily = SfPro,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp
    ),
    h5 = TextStyle(
        fontFamily = SfPro,
        fontWeight = FontWeight.Normal,
        fontSize = 56.sp
    ),
    body1 = TextStyle(
        fontFamily = SfPro,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = SfPro,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
)