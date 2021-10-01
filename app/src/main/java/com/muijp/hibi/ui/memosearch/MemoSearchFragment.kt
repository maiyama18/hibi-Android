package com.muijp.hibi.ui.memosearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.muijp.hibi.database.getDatabase
import com.muijp.hibi.databinding.MemoSearchFragmentBinding
import com.muijp.hibi.repository.MemoRepository
import com.muijp.hibi.ui.recyclerview.memolist.MemoListAdapter
import com.muijp.hibi.ui.recyclerview.memolist.MemoListListener

class MemoSearchFragment : Fragment() {
    private lateinit var viewModel: MemoSearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val database = getDatabase(requireActivity().application)
        val repository = MemoRepository(database)
        viewModel = ViewModelProvider(this, MemoSearchViewModelFactory(repository))
            .get(MemoSearchViewModel::class.java)

        val binding = MemoSearchFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel

        val memoListListener = MemoListListener { memoItem ->
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
}