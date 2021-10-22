package com.muijp.hibi.ui.memosearch

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.muijp.hibi.database.memo.Memo
import com.muijp.hibi.ui.components.MemoItem

@Composable
fun MemoSearchScreen(viewModel: MemoSearchViewModel) {
    Scaffold() {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "メモ検索") },
                )
            },
        ) {
            MemoSearchBody(
                memos = viewModel.memos,
                query = viewModel.query,
                onQueryChanged = viewModel::onQueryChanged,
            )
        }
    }
}

@Composable
fun MemoSearchBody(memos: List<Memo>, query: String, onQueryChanged: (query: String) -> Unit) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
    ) {
        TextField(
            value = query,
            onValueChange = onQueryChanged,
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "検索") },
            modifier = Modifier.fillMaxWidth(),
        )
        
        LazyColumn {
            items(memos) {
                MemoItem(it, onMemoTapped = {})
            }
        }
    }
}