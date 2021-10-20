package com.muijp.hibi.ui.memolist

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable

@Composable
fun MemoListScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "メモ一覧") },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.Search, contentDescription = "メモ検索")
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(Icons.Filled.Edit, contentDescription = "メモ作成")
            }
        }
    ) {
        Text("MemoList")
    }
}