package com.muijp.hibi.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MessageDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MessageDialogFragment(
    private val messageId: Int,
    private val positiveLabelId: Int,
    private val negativeLabelId: Int,
    private val onPositiveTapped: () -> Unit,
    private val onNegativeTapped: () -> Unit,
): DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder
                .setMessage(messageId)
                .setPositiveButton(positiveLabelId) { _, _ ->
                    onPositiveTapped()
                }
                .setNegativeButton(negativeLabelId) { _, _ ->
                    onNegativeTapped()
                }
                .create()
        } ?: throw IllegalStateException("activity is unexpectedly found to be null")
    }
}