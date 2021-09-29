package com.muijp.hibi.ui.memolist

import androidx.lifecycle.*
import com.muijp.hibi.database.memo.Memo
import com.muijp.hibi.repository.MemoRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class MemoListViewModel(
    private val repository: MemoRepository,
): ViewModel() {
    val memos: LiveData<List<Memo>> = repository.observeAll()

    private val _goToMemoEdit = MutableLiveData<String?>()
    val goToMemoEdit: LiveData<String?>
        get() = _goToMemoEdit

    fun goToMemoEdit() {
        val now = LocalDateTime.now()
        val formattedDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(now)
        _goToMemoEdit.value = formattedDate
    }

    fun goToMemoEditComplete() {
        _goToMemoEdit.value = null
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