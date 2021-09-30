package com.muijp.hibi.ui.memoedit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.muijp.hibi.database.memo.Memo
import com.muijp.hibi.repository.MemoRepository
import kotlinx.coroutines.launch

class MemoEditViewModel(
    private val memoId: String?,
    private val repository: MemoRepository,
) : ViewModel() {
    private lateinit var memo: Memo
    val memoText = MutableLiveData<String>()

    val shouldFocusOnStart: Boolean
        get() = (memoId == null) && memoText.value.isNullOrEmpty()

    fun retrieveMemo() {
        viewModelScope.launch {
            var m = if (memoId != null) {
                repository.find(memoId)
            } else {
                null
            }

            if (m == null) {
                m = Memo.new("")
            }

            memo = m
            memoText.value = m.text
        }
    }

    fun onMemoTextUpdated() {
        viewModelScope.launch {
            val memoText = memoText.value
            if (::memo.isInitialized && memoText != null) {
                memo.text = memoText
                repository.upsert(memo)
            }
        }
    }
}

class MemoEditViewModelFactory(
    private var formattedDate: String?,
    private val repository: MemoRepository,
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MemoEditViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MemoEditViewModel(formattedDate, repository) as T
        }
        throw IllegalArgumentException("unable to construct MemoEditViewModel")
    }
}
