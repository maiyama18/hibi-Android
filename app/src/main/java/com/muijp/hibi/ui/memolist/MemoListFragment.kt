package com.muijp.hibi.ui.memolist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.muijp.hibi.database.getDatabase
import com.muijp.hibi.databinding.MemoListFragmentBinding
import com.muijp.hibi.repository.MemoRepository
import timber.log.Timber

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

        viewModel.goToMemoEdit.observe(viewLifecycleOwner) { formattedDate ->
            if (formattedDate != null) {
                findNavController().navigate(
                    MemoListFragmentDirections.actionMemoListFragmentToMemoEditFragment(formattedDate)
                )
                viewModel.goToMemoEditComplete()
            }
        }

        return binding.root
    }
}