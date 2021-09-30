package com.muijp.hibi.ui.memoedit

import androidx.lifecycle.*
import com.muijp.hibi.database.memo.Memo
import com.muijp.hibi.repository.MemoRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class MemoEditViewModel(
    private val formattedDate: String,
    private val repository: MemoRepository,
) : ViewModel() {
    val memoText = MutableLiveData<String>()

    fun reflectSavedMemoText() {
        viewModelScope.launch {
            val memo = repository.findByFormattedDate(formattedDate)
            memoText.value = memo?.memo ?: ""
        }
    }

    fun saveMemo() {
        viewModelScope.launch {
            val memo = Memo(formattedDate, memoText.value ?: "")
            repository.upsert(memo)
        }
    }
}

class MemoEditViewModelFactory(private var formattedDate: String, private val repository: MemoRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MemoEditViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MemoEditViewModel(formattedDate, repository) as T
        }
        throw IllegalArgumentException("unable to construct MemoEditViewModel")
    }
}
