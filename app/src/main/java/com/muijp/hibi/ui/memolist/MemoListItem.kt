package com.muijp.hibi.ui.memolist

import com.muijp.hibi.database.memo.Memo
import java.time.LocalDate

sealed class MemoListItem {
    abstract val id: String

    data class MemoItem(val memo: Memo): MemoListItem() {
        override val id: String = memo.id
    }

    data class HeaderItem(val date: LocalDate): MemoListItem() {
        override val id: String = date.toString()
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