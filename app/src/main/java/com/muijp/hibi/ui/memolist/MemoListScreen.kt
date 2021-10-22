package com.muijp.hibi.ui.memolist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.muijp.hibi.database.memo.Memo
import com.muijp.hibi.extension.formattedDate
import com.muijp.hibi.extension.isScrolledToTheEnd
import com.muijp.hibi.ui.components.EmptyBody
import com.muijp.hibi.ui.components.MemoDateHeader
import com.muijp.hibi.ui.components.MemoItem
import java.time.LocalDate

@Composable
fun MemoListScreen(
    viewModel: MemoListViewModel,
    navToMemoCreate: () -> Unit,
    navToMemoEdit: (id: String) -> Unit,
    navToMemoSearch: () -> Unit,
) {
    val memos by viewModel.memos.observeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "メモ一覧") },
                actions = {
                    IconButton(onClick = navToMemoSearch) {
                        Icon(Icons.Filled.Search, contentDescription = "メモ検索")
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = navToMemoCreate) {
                Icon(Icons.Filled.Edit, contentDescription = "メモ作成")
            }
        }
    ) {
        memos?.let {
            MemoListBody(
                it,
                onScrolledToBottom = { viewModel.onScrolledToBottom() },
                navToMemoEdit = navToMemoEdit,
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MemoListBody(
    memos: Map<LocalDate,List<Memo>>,
    onScrolledToBottom: () -> Unit,
    navToMemoEdit: (id: String) -> Unit,
) {
    val listState = rememberLazyListState()

    if (listState.isScrolledToTheEnd()) {
        onScrolledToBottom()
    }

    if (memos.isEmpty()) {
        EmptyBody()
    } else {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize().padding(start = 16.dp, end = 16.dp, top = 16.dp),
        ) {
            memos.forEach { (date, memosOfDate) ->
                stickyHeader {
                    MemoDateHeader(date.formattedDate)
                }

                items(memosOfDate) {
                    MemoItem(it, onMemoTapped = navToMemoEdit)
                }
            }
        }
    }
}

