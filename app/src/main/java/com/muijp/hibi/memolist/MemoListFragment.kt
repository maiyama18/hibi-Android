package com.muijp.hibi.memolist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.muijp.hibi.R
import com.muijp.hibi.databinding.MemoListFragmentBinding

class MemoListFragment : Fragment() {
    private lateinit var viewModel: MemoListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(MemoListViewModel::class.java)

        val binding = MemoListFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel

        return binding.root
    }
}