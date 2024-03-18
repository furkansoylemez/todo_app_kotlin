package com.furkansoylemez.todoapp.presentation.to_do_list.viewmodel

import com.furkansoylemez.todoapp.domain.model.ToDoTask

data class ToDoListState(
    val isLoading: Boolean = false,
    val tasks: List<ToDoTask> = emptyList(),
    val error: String? = null
)