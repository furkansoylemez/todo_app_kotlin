package com.furkansoylemez.todoapp.presentation.to_do_list.view

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.furkansoylemez.todoapp.R
import com.furkansoylemez.todoapp.databinding.DialogFragmentAddToDoBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AddToDoDialogFragment : DialogFragment() {

    private var _binding: DialogFragmentAddToDoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogFragmentAddToDoBinding.inflate(layoutInflater)
        return MaterialAlertDialogBuilder(requireContext())
            .setView(binding.root)
            .setTitle(getString(R.string.add_to_do_dialog_title))
            .setPositiveButton(getString(R.string.add_to_do_dialog_add_text)) { _, _ ->
                val title = binding.titleEditText.text.toString()
                val description = binding.descriptionEditText.text.toString()
                val bundle = Bundle().apply {
                    putString("title", title)
                    putString("description", description)
                }
                setFragmentResult("addRequestKey", bundle)
                findNavController().popBackStack()
            }
            .setNegativeButton(getString(R.string.add_to_do_dialog_cancel_text) ){_,_->
                findNavController().popBackStack()
            }
            .create()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}