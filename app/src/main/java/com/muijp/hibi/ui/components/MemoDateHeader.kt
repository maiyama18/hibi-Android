package com.muijp.hibi.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun MemoDateHeader(formattedDate: String) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .padding(bottom = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.secondary),
            shape = RoundedCornerShape(percent = 50),
        ) {
            Text(
                formattedDate,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Medium),
            )
        }
        Divider(color = MaterialTheme.colors.secondary)
    }
}
