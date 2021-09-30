package com.muijp.hibi.ui.memolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.muijp.hibi.database.memo.Memo
import com.muijp.hibi.repository.MemoRepository

class MemoListViewModel(
    repository: MemoRepository,
): ViewModel() {
    val memos: LiveData<List<Memo>> = repository.observeAll()

    private val _goToMemoCreate = MutableLiveData<Boolean>()
    val goToMemoCreate: LiveData<Boolean>
        get() = _goToMemoCreate

    fun goToMemoCreate() {
        _goToMemoCreate.value = true
    }

    fun goToMemoCreateComplete() {
        _goToMemoCreate.value = false
    }
}

class MemoListViewModelFactory(private val repository: MemoRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MemoListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MemoListViewModel(repository) as T
        }
        throw IllegalArgumentException("unable to construct MemoListViewModel")
    }
}