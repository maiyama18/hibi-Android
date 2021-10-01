package com.muijp.hibi.ui.recyclerview.memolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.muijp.hibi.databinding.MemoListHeaderItemBinding
import com.muijp.hibi.databinding.MemoListMemoItemBinding
import java.security.InvalidParameterException

class MemoListAdapter(
    private val listener: MemoListListener,
): ListAdapter<MemoListItem, RecyclerView.ViewHolder>(DiffCallback) {
    companion object {
        object DiffCallback: DiffUtil.ItemCallback<MemoListItem>() {
            override fun areItemsTheSame(oldItem: MemoListItem, newItem: MemoListItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MemoListItem, newItem: MemoListItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    class MemoViewHolder(private val binding: MemoListMemoItemBinding): RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): MemoViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = MemoListMemoItemBinding.inflate(inflater, parent, false)
                return MemoViewHolder(binding)
            }
        }

        fun bind(item: MemoListItem.MemoItem, listener: MemoListListener) {
            binding.memoItem = item
            binding.listener = listener
        }
    }

    class HeaderViewHolder(private val binding: MemoListHeaderItemBinding): RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = MemoListHeaderItemBinding.inflate(inflater, parent, false)
                return HeaderViewHolder(binding)
            }
        }

        fun bind(item: MemoListItem.HeaderItem) {
            binding.headerItem = item
        }
    }

    private val itemViewTypeHeader = 0
    private val itemViewTypeMemo = 1

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is MemoListItem.HeaderItem -> itemViewTypeHeader
            is MemoListItem.MemoItem -> itemViewTypeMemo
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            itemViewTypeHeader -> HeaderViewHolder.from(parent)
            itemViewTypeMemo -> MemoViewHolder.from(parent)
            else -> throw InvalidParameterException("invalid viewType: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> {
                val item = getItem(position) as MemoListItem.HeaderItem
                holder.bind(item)
            }
            is MemoViewHolder -> {
                val item = getItem(position) as MemoListItem.MemoItem
                holder.bind(item, listener)
            }
        }
    }
}