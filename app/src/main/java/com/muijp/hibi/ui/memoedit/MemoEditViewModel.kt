package com.muijp.hibi.ui.memoedit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.muijp.hibi.database.memo.Memo
import com.muijp.hibi.repository.MemoRepository
import com.muijp.hibi.ui.memolist.MemoListViewModel
import java.lang.IllegalArgumentException

class MemoEditViewModel(
    private val formattedDate: String,
    private val repository: MemoRepository,
) : ViewModel() {
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
