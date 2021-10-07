package com.muijp.hibi.ui.memoedit

import androidx.lifecycle.*
import com.muijp.hibi.R
import com.muijp.hibi.database.memo.Memo
import com.muijp.hibi.extension.formattedDateTime
import com.muijp.hibi.provider.StringProvider
import com.muijp.hibi.repository.MemoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemoEditViewModel @Inject constructor(
    private val repository: MemoRepository,
    private val stringProvider: StringProvider,
    private val state: SavedStateHandle,
): ViewModel() {
    private val memoId: String?
        get() = state.get<String>("id")

    private lateinit var memo: Memo

    val memoText = MutableLiveData<String>()

    private val _title = MutableLiveData<String>()
    val title: LiveData<String>
        get() = _title

    private val _backToPrevious = MutableLiveData<Boolean>()
    val backToPrevious: LiveData<Boolean>
        get() = _backToPrevious

    val shouldFocusOnStart: Boolean
        get() = (memoId == null) && memoText.value.isNullOrEmpty()

    fun retrieveMemo() {
        viewModelScope.launch {
            val m = if (memoId != null) {
                repository.find(memoId!!)
            } else {
                null
            }

            if (m != null) {
                memo = m
                memoText.value = m.text
                _title.value = m.createdAt.formattedDateTime
            } else {
                memo = Memo.new("")
                _title.value = stringProvider.getString(R.string.new_memo)
            }
        }
    }

    fun onMemoTextUpdated() {
        viewModelScope.launch {
            val memoText = memoText.value
            if (::memo.isInitialized && !memoText.isNullOrEmpty()) {
                memo.text = memoText
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
