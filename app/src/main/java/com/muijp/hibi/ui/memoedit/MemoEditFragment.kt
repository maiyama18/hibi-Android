package com.muijp.hibi.ui.memoedit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.muijp.hibi.R
import com.muijp.hibi.database.getDatabase
import com.muijp.hibi.databinding.MemoEditFragmentBinding
import com.muijp.hibi.extension.focus
import com.muijp.hibi.repository.MemoRepository
import com.muijp.hibi.ui.MainActivity
import com.muijp.hibi.ui.dialog.MessageDialogFragment

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
        viewModel = ViewModelProvider(this, MemoEditViewModelFactory( requireActivity().application, args.id, repository))
            .get(MemoEditViewModel::class.java)

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

        setupToolbar()

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        if (viewModel.shouldFocusOnStart) {
            binding.memoEditText.focus()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

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

