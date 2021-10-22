package com.muijp.hibi.ui.memoedit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
): ViewModel() {
    private lateinit var memo: Memo

    var shouldFocusOnStart: Boolean = false

    var inputText by mutableStateOf("")
        private set

    var title by mutableStateOf("")
        private set

    private val _backToPrevious = MutableLiveData<Boolean>()
    val backToPrevious: LiveData<Boolean>
        get() = _backToPrevious

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
                shouldFocusOnStart = false
            } else {
                memo = Memo.new("")
                title = "メモ作成"
                shouldFocusOnStart = true
            }
        }
    }

    fun onMemoTextUpdated(updatedText: String) {
        viewModelScope.launch {
            inputText = updatedText
            if (::memo.isInitialized && updatedText.isNotEmpty()) {
                memo.text = updatedText
                repository.upsert(memo)
            }
        }
    }

    fun onMemoDeleted() {
        viewModelScope.launch {
            repository.delete(memo)
            _backToPrevious.value = true
        }
    }
}
