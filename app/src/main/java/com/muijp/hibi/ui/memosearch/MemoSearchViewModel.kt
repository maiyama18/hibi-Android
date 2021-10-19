package com.muijp.hibi.ui.memosearch

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
class MemoSearchViewModel @Inject constructor(
    private val repository: MemoRepository,
): ViewModel() {
    val query = MutableLiveData<String>()

    private val memos = MutableLiveData<List<Memo>>()

    private val _goToMemoEdit = MutableLiveData<Boolean>()
    val goToMemoEdit: LiveData<Boolean>
        get() = _goToMemoEdit

    fun goToMemoEdit() {
        _goToMemoEdit.value = true
    }

    fun goToMemoEditComplete() {
        _goToMemoEdit.value = false
    }

    fun searchMemos() {
        val query = query.value?.trim()
        if (query.isNullOrEmpty()) {
            memos.value = emptyList()
            return
        }

        viewModelScope.launch {
            memos.value = repository.search(query)
        }
    }
}
