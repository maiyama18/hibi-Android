package com.muijp.hibi.ui.memosearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.muijp.hibi.databinding.MemoSearchFragmentBinding
import com.muijp.hibi.ui.recyclerview.memolist.MemoListAdapter
import com.muijp.hibi.ui.recyclerview.memolist.MemoListListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MemoSearchFragment : Fragment() {
    private val viewModel: MemoSearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = MemoSearchFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel

        val memoListListener = MemoListListener { memoItem ->
            goToMemoEditFragment(memoItem.id)
        }
        val adapter = MemoListAdapter(memoListListener)
        binding.memoSearchedListRecyclerView.adapter = adapter
        viewModel.items.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.query.observe(viewLifecycleOwner) {
            viewModel.searchMemos()
        }

        return binding.root
    }

    private fun goToMemoEditFragment(memoId: String?) {
        findNavController().navigate(
            MemoSearchFragmentDirections.actionMemoSearchFragmentToMemoEditFragment(memoId)
        )
        viewModel.goToMemoEditComplete()
    }
}