package com.furkansoylemez.todoapp.presentation.to_do_list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkansoylemez.todoapp.domain.model.ToDoTask
import com.furkansoylemez.todoapp.domain.usecase.AddToDoTaskUseCase
import com.furkansoylemez.todoapp.domain.usecase.DeleteToDoTaskUseCase
import com.furkansoylemez.todoapp.domain.usecase.GetToDoTasksUseCase
import com.furkansoylemez.todoapp.domain.usecase.UpdateToDoTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoListViewModel @Inject constructor(
    private val getToDoTasksUseCase: GetToDoTasksUseCase,
    private val addToDoTaskUseCase: AddToDoTaskUseCase,
    private val updateToDoTaskUseCase: UpdateToDoTaskUseCase,
    private val deleteToDoTaskUseCase: DeleteToDoTaskUseCase
) : ViewModel() {
    var uiState: StateFlow<ToDoListState>

    init {
        uiState = getToDoTasksUseCase().map { toDos ->
            ToDoListState.Success(toDos)
        }.stateIn(
            scope = viewModelScope,
            initialValue = ToDoListState.Loading,
            started = SharingStarted.WhileSubscribed(3000)
        )
    }

    fun addTask(title: String, description: String) {
        val newTask = ToDoTask(title = title, description = description, isCompleted = false)
        viewModelScope.launch {
            addToDoTaskUseCase(newTask)
        }
    }

    fun updateTask(task: ToDoTask) {
        viewModelScope.launch {
            updateToDoTaskUseCase(task)
        }
    }

    fun deleteTask(task: ToDoTask) {
        viewModelScope.launch {
            deleteToDoTaskUseCase(task)
        }
    }
}