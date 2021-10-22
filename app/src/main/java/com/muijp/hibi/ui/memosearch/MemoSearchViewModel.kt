package com.muijp.hibi.ui.memosearch

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
    companion object {
        const val LIMIT = 50
    }

    var query by mutableStateOf("")
    var memos by mutableStateOf<List<Memo>>(emptyList())

    fun onQueryChanged(query: String) {
        this.query = query.trim()
        searchMemos(query)
    }

    private fun searchMemos(query: String) {
        if (query.isEmpty()) {
            memos = emptyList()
            return
        }

        viewModelScope.launch {
            memos = repository.search(query, LIMIT)
        }
    }
}
