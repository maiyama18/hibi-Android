package com.muijp.hibi.ui.memolist

import androidx.lifecycle.*
import com.muijp.hibi.database.memo.Memo
import com.muijp.hibi.repository.MemoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MemoListViewModel @Inject constructor(
    private val repository: MemoRepository,
) : ViewModel() {
    companion object {
        const val LIMIT_UNIT = 10
    }

    private val limit: MutableLiveData<Int> = MutableLiveData(LIMIT_UNIT)
    private val memoList: LiveData<List<Memo>> = Transformations.switchMap(limit) {
        repository.liveDataByLimit(it)
    }
    val memos: LiveData<Map<LocalDate, List<Memo>>> = Transformations.map(memoList) { memoList ->
        Timber.d("memos count: ${memoList.size}")
        memoList.groupBy { memo -> memo.createdAt.toLocalDate() }
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

    private var scrolledToBottomOnLimit = 0

    fun onScrolledToBottom() {
        viewModelScope.launch {
            val currentMemosCount = memoList.value?.size ?: 0
            val allMemosCount = repository.count()
            if (currentMemosCount >= allMemosCount) {
                return@launch
            }

            limit.value = currentMemosCount + LIMIT_UNIT
        }
    }
}
