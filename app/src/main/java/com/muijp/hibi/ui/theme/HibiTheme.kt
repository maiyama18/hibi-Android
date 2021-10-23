package com.muijp.hibi.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun HibiTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = lightColors,
        typography = typography,
        content = content,
    )
}