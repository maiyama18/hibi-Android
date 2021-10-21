package com.muijp.hibi.ui.memolist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.muijp.hibi.database.memo.Memo
import com.muijp.hibi.extension.formattedDate
import com.muijp.hibi.extension.formattedTime
import java.time.LocalDate

@Composable
fun MemoListScreen(
    viewModel: MemoListViewModel,
) {
    val memos by viewModel.memos.observeAsState()

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
        memos?.let {
            MemoListBody(it)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MemoListBody(memos: Map<LocalDate, List<Memo>>) {
    if (memos.isEmpty()) {
        MemoListEmptyBody()
    } else {
        LazyColumn(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
        ) {
            memos.forEach { (date, memosOfDate) ->
                stickyHeader {
                    Header(date.formattedDate)
                }

                items(memosOfDate) {
                    MemoItem(it)
                }
            }
        }
    }
}

@Composable
fun MemoListEmptyBody() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            "まだメモがありません",
        )
    }
}

@Composable
fun Header(formattedDate: String) {
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
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.subtitle2,
            )
        }
        Divider(color = MaterialTheme.colors.secondary)
    }
}

@Composable
fun MemoItem(memo: Memo) {
    Column(
        modifier = Modifier.padding(vertical = 6.dp)
    ) {
        MemoBalloon(memo.text)

        Text(
            memo.createdAt.formattedTime,
            style = MaterialTheme.typography.caption,
        )
    }
}

@Composable
fun MemoBalloon(text: String) {
    Surface(
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomEnd = 16.dp),
    ) {
        Text(
            text,
            modifier = Modifier
                .background(MaterialTheme.colors.primary.copy(alpha = 0.1f))
                .padding(12.dp),
            style = MaterialTheme.typography.body1
        )
    }
}