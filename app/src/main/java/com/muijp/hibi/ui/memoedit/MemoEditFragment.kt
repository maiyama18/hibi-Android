package com.muijp.hibi.ui.memoedit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.muijp.hibi.database.getDatabase
import com.muijp.hibi.databinding.MemoEditFragmentBinding
import com.muijp.hibi.repository.MemoRepository

class MemoEditFragment : Fragment() {
    private lateinit var binding: MemoEditFragmentBinding
    private lateinit var viewModel: MemoEditViewModel
    private val args: MemoEditFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val database = getDatabase(requireActivity().application)
        val repository = MemoRepository(database)
        viewModel = ViewModelProvider(this, MemoEditViewModelFactory(args.id, repository))
            .get(MemoEditViewModel::class.java)

        binding = MemoEditFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.retrieveMemo()

        viewModel.memoText.observe(viewLifecycleOwner) {
            viewModel.onMemoTextUpdated()
        }

        return binding.root
    }
}

