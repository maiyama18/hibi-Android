package com.muijp.hibi.ui.memoedit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muijp.hibi.database.memo.Memo
import com.muijp.hibi.repository.MemoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemoEditViewModel @Inject constructor(
    private val repository: MemoRepository,
) : ViewModel() {
    private lateinit var memo: Memo

    var inputText by mutableStateOf("")
        private set

    var title by mutableStateOf("")
        private set

    var isDeleteDialogOpen by mutableStateOf(false)
        private set

    var navToBack by mutableStateOf(false)
        private set

    var isNewMemo: Boolean = false

    val isMemoSaved: Boolean
        get() = ::memo.isInitialized && inputText.isNotEmpty()

    fun retrieveMemo(memoId: String?) {
        viewModelScope.launch {
            val m = if (memoId != null) {
                repository.find(memoId)
            } else {
                null
            }

            if (m != null) {
                memo = m
                inputText = m.text
                title = "メモ編集"
                isNewMemo = false
            } else {
                memo = Memo.new("")
                title = "メモ作成"
                isNewMemo = true
            }
        }
    }

    fun onMemoTextUpdated(updatedText: String) {
        viewModelScope.launch {
            if (!::memo.isInitialized) {
                return@launch
            }

            inputText = updatedText
            if (updatedText.isEmpty()) {
                repository.delete(memo)
            } else {
                memo.text = updatedText
                repository.upsert(memo)
            }
        }
    }

    fun openMemoDeleteDialog() {
        isDeleteDialogOpen = true
    }

    fun closeMemoDeleteDialog() {
        isDeleteDialogOpen = false
    }

    fun onMemoDeleted() {
        viewModelScope.launch {
            repository.delete(memo)
            closeMemoDeleteDialog()
            navToBack = true
        }
    }

    fun onNavToBackCompleted() {
        navToBack = false
    }
}
