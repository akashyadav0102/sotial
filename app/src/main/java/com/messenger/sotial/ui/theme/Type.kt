package com.messenger.sotial.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.messenger.sotial.R

// Set of Material typography styles to start with

val latoBlack = FontFamily(
    Font(R.font.latoblack)
)

val latoBold = FontFamily(
    Font(R.font.latobold)
)

val Typography = Typography(

    h1 = TextStyle(
        fontFamily = latoBlack,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),

    h2 = TextStyle(
        fontFamily = latoBold,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),

    button = TextStyle(
        fontFamily = latoBlack,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    /*
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)
