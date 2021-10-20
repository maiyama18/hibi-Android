package com.muijp.hibi.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.muijp.hibi.R

private val mPlusFontFamily = FontFamily(
    Font(R.font.mplus1p_thin, FontWeight.Thin),
    Font(R.font.mplus1p_light, FontWeight.Light),
    Font(R.font.mplus1p_regular, FontWeight.Normal),
    Font(R.font.mplus1p_medium, FontWeight.Medium),
    Font(R.font.mplus1p_bold, FontWeight.Bold),
)

val typography = Typography(
    defaultFontFamily = mPlusFontFamily,
)