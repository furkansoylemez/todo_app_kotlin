package com.furkansoylemez.todoapp.presentation.to_do_list.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
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
        setupRecyclerView()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                binding.progressBar.isVisible = state.isLoading
                binding.toDoListRecyclerView.isVisible = !state.isLoading
                if (state.error != null) {
                    // Show error state
                } else {
                    (binding.toDoListRecyclerView.adapter as ToDoListAdapter).submitList(state.tasks)
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.toDoListRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.toDoListRecyclerView.adapter = ToDoListAdapter()
    }


}