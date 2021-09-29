package com.muijp.hibi.ui.memoedit

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.muijp.hibi.database.getDatabase
import com.muijp.hibi.databinding.MemoEditFragmentBinding
import com.muijp.hibi.repository.MemoRepository

class MemoEditFragment : Fragment() {
    private lateinit var viewModel: MemoEditViewModel
    private val args: MemoEditFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val database = getDatabase(requireActivity().application)
        val repository = MemoRepository(database)
        viewModel = ViewModelProvider(this, MemoEditViewModelFactory(args.formattedDate, repository))
            .get(MemoEditViewModel::class.java)

        val binding = MemoEditFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }
}