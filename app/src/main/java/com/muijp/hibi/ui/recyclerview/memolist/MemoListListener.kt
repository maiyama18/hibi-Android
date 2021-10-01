package com.muijp.hibi.ui.recyclerview.memolist

class MemoListListener(
    private var onClickMemoItem: (memoItem: MemoListItem.MemoItem) -> Unit,
) {
    fun handleClickMemoItem(memoItem: MemoListItem.MemoItem) = onClickMemoItem(memoItem)
}