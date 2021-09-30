package com.muijp.hibi.ui.memolist

class MemoListListener(
    private var onClickMemoItem: (memoItem: MemoListItem.MemoItem) -> Unit,
) {
    fun handleClickMemoItem(memoItem: MemoListItem.MemoItem) = onClickMemoItem(memoItem)
}