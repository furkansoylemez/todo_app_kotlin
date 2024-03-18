package com.furkansoylemez.todoapp.presentation.to_do_list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkansoylemez.todoapp.domain.usecase.GetToDoTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoListViewModel @Inject constructor(
    private val getToDoTasksUseCase: GetToDoTasksUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ToDoListState())
    val uiState: StateFlow<ToDoListState> = _uiState

    init {
        fetchToDoTasks()
    }

    private fun fetchToDoTasks() {
        viewModelScope.launch {
            _uiState.value = ToDoListState(isLoading = true)
            getToDoTasksUseCase()
                .catch { e ->
                    _uiState.value = ToDoListState(error = e.message)
                }.collect { tasks ->
                    _uiState.update { currentState ->
                        currentState.copy(isLoading = false, tasks = tasks, error = null)
                    }
                }
        }
    }
}