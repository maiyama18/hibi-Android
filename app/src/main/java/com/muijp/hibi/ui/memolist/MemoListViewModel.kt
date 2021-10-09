package com.muijp.hibi.ui.memolist

import androidx.lifecycle.*
import com.muijp.hibi.database.memo.Memo
import com.muijp.hibi.repository.MemoRepository
import com.muijp.hibi.ui.recyclerview.memolist.memosToMemoListItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MemoListViewModel @Inject constructor(
    private val repository: MemoRepository,
) : ViewModel() {
    companion object {
        const val LIMIT_UNIT = 10
    }

    private val limit: MutableLiveData<Int> = MutableLiveData(LIMIT_UNIT)
    private val memos: LiveData<List<Memo>> = Transformations.switchMap(limit) {
        repository.liveDataByLimit(it)
    }
    val items = Transformations.map(memos) { memos ->
        Timber.d("memos first: ${memos.firstOrNull()?.text}")
        memosToMemoListItems(memos)
    }

    private val _goToMemoCreate = MutableLiveData<Boolean>()
    val goToMemoCreate: LiveData<Boolean>
        get() = _goToMemoCreate

    fun goToMemoCreate() {
        _goToMemoCreate.value = true
    }

    fun goToMemoCreateComplete() {
        _goToMemoCreate.value = false
    }

    fun onScrolledToBottom() {
        val currentMemosCount = memos.value?.size ?: 0
        viewModelScope.launch {
            val allMemosCount = repository.count()
            if (currentMemosCount >= allMemosCount) {
                return@launch
            }

            limit.value = currentMemosCount + LIMIT_UNIT
        }
    }
}
