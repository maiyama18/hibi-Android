package com.muijp.hibi.ui.memolist

import com.muijp.hibi.database.memo.Memo
import com.muijp.hibi.extension.formattedDate
import com.muijp.hibi.extension.formattedTime
import java.time.LocalDate

sealed class MemoListItem {
    abstract val id: String

    data class MemoItem(private val memo: Memo): MemoListItem() {
        override val id: String = memo.id

        val text: String
            get() = memo.text

        val formattedTime: String
            get() = memo.createdAt.formattedTime
    }

    data class HeaderItem(private val date: LocalDate): MemoListItem() {
        override val id: String = date.toString()
        val formattedDate: String
            get() = date.formattedDate
    }
}

fun memosToMemoListItems(memos: List<Memo>): List<MemoListItem> {
    val map = memos.groupBy { it.createdAt.toLocalDate() }
    val items = mutableListOf<MemoListItem>()
    map.forEach { (date, memos) ->
        items.add(MemoListItem.HeaderItem(date))
        items.addAll(memos.map { MemoListItem.MemoItem(it) })
    }
    return items
}