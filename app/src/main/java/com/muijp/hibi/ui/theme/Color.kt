package com.muijp.hibi.ui.theme

import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

private val blueGray800 = Color(0xFF37474F)
private val blueGray900 = Color(0xFF263238)
private val red700 = Color(0xFFD32F2F)
private val red900 = Color(0xFFB71C1C)
private val gray900 = Color(0xFF212121)

val lightColors = lightColors(
    primary = blueGray800,
    primaryVariant = blueGray900,
    secondary = red700,
    secondaryVariant = red900,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = gray900,
    onSurface = gray900,
)