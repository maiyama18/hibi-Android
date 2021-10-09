package com.muijp.hibi.ui.memolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.muijp.hibi.R
import com.muijp.hibi.databinding.MemoListFragmentBinding
import com.muijp.hibi.ui.MainActivity
import com.muijp.hibi.ui.recyclerview.memolist.MemoListAdapter
import com.muijp.hibi.ui.recyclerview.memolist.MemoListListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MemoListFragment : Fragment() {
    private val viewModel: MemoListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = MemoListFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel

        val memoListListener = MemoListListener { memoItem ->
            goToMemoEditFragment(memoItem.id)
        }
        val adapter = MemoListAdapter(memoListListener)
        binding.memoListRecyclerView.adapter = adapter
        viewModel.items.observe(viewLifecycleOwner) { memoItems ->
            if (memoItems.isEmpty()) {
                binding.memoListRecyclerView.visibility = View.GONE
                binding.emptyTextView.visibility = View.VISIBLE
            } else {
                binding.memoListRecyclerView.visibility = View.VISIBLE
                binding.emptyTextView.visibility = View.GONE
            }
            adapter.submitList(memoItems)
        }
        binding.memoListRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = binding.memoListRecyclerView.layoutManager as? LinearLayoutManager ?: return
                if (layoutManager.findLastVisibleItemPosition() == layoutManager.itemCount - 1) {
                    viewModel.onScrolledToBottom()
                }
            }
        })

        viewModel.goToMemoCreate.observe(viewLifecycleOwner) {
            if (it == true) {
                goToMemoEditFragment(null)
            }
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        setupToolbar()
    }

    override fun onStop() {
        super.onStop()

        teardownToolbar()
    }

    private fun goToMemoEditFragment(memoId: String?) {
        findNavController().navigate(
            MemoListFragmentDirections.actionMemoListFragmentToMemoEditFragment(memoId)
        )
        viewModel.goToMemoCreateComplete()
    }

    private fun setupToolbar() {
        setHasOptionsMenu(true)

        val toolbar = (activity as? MainActivity)?.toolbar
        toolbar?.let {
            it.menu.clear()
            it.inflateMenu(R.menu.memo_list_menu)
            it.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_search_memo -> {
                        findNavController().navigate(
                            MemoListFragmentDirections.actionMemoListFragmentToMemoSearchFragment()
                        )
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun teardownToolbar() {
        setHasOptionsMenu(false)

        val toolbar = (activity as? MainActivity)?.toolbar
        toolbar?.menu?.clear()
    }
}