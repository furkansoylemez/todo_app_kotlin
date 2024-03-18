package com.furkansoylemez.todoapp.presentation.to_do_list.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.clearFragmentResultListener
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.furkansoylemez.todoapp.R
import com.furkansoylemez.todoapp.databinding.FragmentToDoListBinding
import com.furkansoylemez.todoapp.presentation.to_do_list.viewmodel.ToDoListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ToDoListFragment : Fragment() {

    private var _binding: FragmentToDoListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ToDoListViewModel by viewModels()

    private lateinit var toDoListAdapter: ToDoListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentToDoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeViewModel()
    }

    private fun setupView() {
        setupButtons()
        setupRecyclerView()
    }

    private fun setupButtons() {
        binding.addToDoFloatingActionButton.setOnClickListener {
            setFragmentResultListener("addRequestKey") { requestKey, bundle ->
                clearFragmentResultListener(requestKey)
                val title = bundle.getString("title", "")
                val description = bundle.getString("description", "")
                if (title.isNotEmpty() && description.isNotEmpty()) {
                    viewModel.addTask(title, description)
                }
            }
            findNavController().navigate(R.id.action_toDoListFragment_to_addToDoDialogFragment)
        }
    }
    private fun setupRecyclerView() {
        toDoListAdapter = ToDoListAdapter()
        binding.toDoListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = toDoListAdapter
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                binding.progressBar.isVisible = state.isLoading
                binding.toDoListRecyclerView.isVisible = !state.isLoading
                if (state.error != null) {
                    Toast.makeText(context, state.error, Toast.LENGTH_LONG).show()
                } else {
                    toDoListAdapter.submitList(state.tasks)
                }
            }
        }
    }




}