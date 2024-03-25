package com.furkansoylemez.todoapp.presentation.to_do_list.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.clearFragmentResultListener
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.furkansoylemez.todoapp.R
import com.furkansoylemez.todoapp.databinding.FragmentToDoListBinding
import com.furkansoylemez.todoapp.presentation.to_do_list.viewmodel.ToDoListState
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
            showAddToDoDialog()
        }
    }

    private fun showAddToDoDialog() {
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

    private fun setupRecyclerView() {
        toDoListAdapter = ToDoListAdapter(onCheckClick = { task ->
            val updatedTask = task.copy(isCompleted = !task.isCompleted)
            viewModel.updateTask(updatedTask)
        }, onItemDelete = { task ->
            viewModel.deleteTask(task)
        })
        binding.toDoListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = toDoListAdapter
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    val isLoading = state is ToDoListState.Loading
                    binding.progressBar.isVisible = isLoading
                    binding.toDoListRecyclerView.isVisible = !isLoading
                    if (state is ToDoListState.Success) {
                        toDoListAdapter.submitList(state.tasks)
                    }
                }
            }
        }
    }
}