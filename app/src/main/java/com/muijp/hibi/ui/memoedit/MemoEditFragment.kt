package com.muijp.hibi.ui.memoedit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.muijp.hibi.R
import com.muijp.hibi.databinding.MemoEditFragmentBinding
import com.muijp.hibi.extension.focus
import com.muijp.hibi.ui.MainActivity
import com.muijp.hibi.ui.dialog.MessageDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MemoEditFragment : Fragment() {
    private lateinit var binding: MemoEditFragmentBinding
    private val viewModel: MemoEditViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MemoEditFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.retrieveMemo()

        viewModel.memoText.observe(viewLifecycleOwner) {
            viewModel.onMemoTextUpdated()
        }

        viewModel.title.observe(viewLifecycleOwner) {
            if (it != null) {
                (activity as MainActivity).setToolbarTitle(it)
            }
        }

        viewModel.backToPrevious.observe(viewLifecycleOwner) {
            if (it == true) {
                activity?.onBackPressed()
            }
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        if (viewModel.shouldFocusOnStart) {
            binding.memoEditText.focus()
        }
        setupToolbar()
    }

    override fun onStop() {
        super.onStop()

        teardownToolbar()
    }

    private fun setupToolbar() {
        setHasOptionsMenu(true)

        val toolbar = (activity as? MainActivity)?.toolbar
        toolbar?.let {
            it.menu.clear()
            it.inflateMenu(R.menu.memo_edit_menu)
            it.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_delete_memo -> {
                        MessageDialogFragment(
                            R.string.delete_memo_message,
                            R.string.delete,
                            R.string.cancel,
                            { viewModel.onMemoDeleted() },
                            {}
                        ).show(parentFragmentManager, "delete_memo")
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

