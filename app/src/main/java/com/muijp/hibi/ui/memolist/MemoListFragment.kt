package com.muijp.hibi.ui.memolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.muijp.hibi.database.getDatabase
import com.muijp.hibi.databinding.MemoListFragmentBinding
import com.muijp.hibi.repository.MemoRepository

class MemoListFragment : Fragment() {
    private lateinit var viewModel: MemoListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val database = getDatabase(requireActivity().application)
        val repository = MemoRepository(database)
        viewModel = ViewModelProvider(this, MemoListViewModelFactory(repository))
            .get(MemoListViewModel::class.java)

        val binding = MemoListFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel

        val adapter = MemoListAdapter()
        binding.memoListRecyclerView.adapter = adapter
        viewModel.memos.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.goToMemoCreate.observe(viewLifecycleOwner) {
            if (it == true) {
                findNavController().navigate(
                    MemoListFragmentDirections.actionMemoListFragmentToMemoEditFragment(null)
                )
                viewModel.goToMemoCreateComplete()
            }
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
    }
}