package com.muijp.hibi.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.muijp.hibi.database.memo.Memo
import com.muijp.hibi.extension.formattedDateTime
import com.muijp.hibi.extension.formattedTime

@Composable
fun MemoItem(memo: Memo, onMemoTapped: (id: String) -> Unit, showFullDateTime: Boolean = false) {
    Column(
        modifier = Modifier.padding(vertical = 6.dp),
    ) {
        MemoBalloon(memo.text, onTapped = { onMemoTapped(memo.id) })

        Text(
            if (showFullDateTime) memo.createdAt.formattedDateTime else memo.createdAt.formattedTime,
            style = MaterialTheme.typography.caption,
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MemoBalloon(text: String, onTapped: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomEnd = 16.dp),
        onClick = onTapped,
    ) {
        Text(
            text,
            modifier = Modifier
                .background(MaterialTheme.colors.primary.copy(alpha = 0.1f))
                .padding(horizontal = 12.dp, vertical = 8.dp),
            style = MaterialTheme.typography.body1
        )
    }
}
