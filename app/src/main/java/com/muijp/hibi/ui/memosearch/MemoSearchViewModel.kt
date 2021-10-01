package com.muijp.hibi.ui.memosearch

import androidx.lifecycle.*
import com.muijp.hibi.database.memo.Memo
import com.muijp.hibi.repository.MemoRepository
import com.muijp.hibi.ui.recyclerview.memolist.memosToMemoListItems
import kotlinx.coroutines.launch

class MemoSearchViewModel(
    private val repository: MemoRepository,
): ViewModel() {
    val query = MutableLiveData<String>()

    private val memos = MutableLiveData<List<Memo>>()
    val items = Transformations.map(memos) { memos ->
        memosToMemoListItems(memos)
    }

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
            memos.value = arrayListOf()
            return
        }

        viewModelScope.launch {
            memos.value = repository.search(query)
        }
    }
}

class MemoSearchViewModelFactory(private val repository: MemoRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MemoSearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MemoSearchViewModel(repository) as T
        }
        throw IllegalArgumentException("unable to construct MemoSearchViewModel")
    }
}
