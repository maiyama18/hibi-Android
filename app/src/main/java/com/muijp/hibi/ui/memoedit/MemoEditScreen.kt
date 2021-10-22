package com.muijp.hibi.ui.memoedit

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp

@Composable
fun MemoEditScreen(
    viewModel: MemoEditViewModel,
    navToBack: () -> Unit,
) {
    val focusRequester = remember { FocusRequester() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = viewModel.title) },
                navigationIcon = {
                    IconButton(onClick = navToBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "戻る")
                    }
                },
                actions = {
                    if (viewModel.isMemoSaved) {
                        IconButton(onClick = viewModel::openMemoDeleteDialog) {
                            Icon(Icons.Filled.Delete, contentDescription = "メモ削除")
                        }
                    }
                },
            )
        },
    ) {
        BasicTextField(
            value = viewModel.inputText,
            onValueChange = { viewModel.onMemoTextUpdated(it) },
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .focusRequester(focusRequester),
            textStyle = MaterialTheme.typography.body1,
        )

        MemoDeleteDialog(
            isOpen = viewModel.isDeleteDialogOpen,
            close = viewModel::closeMemoDeleteDialog,
            deleteMemo = viewModel::onMemoDeleted,
        )

        SideEffect {
            if (viewModel.isNewMemo) {
                focusRequester.requestFocus()
            }

            if (viewModel.navToBack) {
                navToBack()
                viewModel.onNavToBackCompleted()
            }
        }
    }
}

@Composable
fun MemoDeleteDialog(isOpen: Boolean, close: () -> Unit, deleteMemo: () -> Unit) {
    if (isOpen) {
        AlertDialog(
            onDismissRequest = close,
            title = {
                Text("このメモを削除しますか？")
            },
            confirmButton = {
                TextButton(onClick = deleteMemo) {
                    Text("削除")
                }
            },
            dismissButton = {
                TextButton(onClick = close) {
                    Text("キャンセル")
                }
            }
        )
    }
}