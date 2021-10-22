package com.muijp.hibi.ui.memosearch

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import com.muijp.hibi.database.memo.Memo
import com.muijp.hibi.ui.components.MemoItem

@Composable
fun MemoSearchScreen(
    viewModel: MemoSearchViewModel,
    navToBack: () -> Unit,
) {
    Scaffold() {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "メモ検索") },
                    navigationIcon = {
                        IconButton(onClick = navToBack) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "戻る")
                        }
                    }
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
    val focusRequester = remember { FocusRequester() }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
    ) {
        TextField(
            value = query,
            onValueChange = onQueryChanged,
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "検索") },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
        )
        
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(memos) {
                MemoItem(it, onMemoTapped = {}, showFullDateTime = true)
            }
        }

        SideEffect {
            focusRequester.requestFocus()
        }
    }
}