package com.furkansoylemez.todoapp.presentation.to_do_list.viewmodel

import com.furkansoylemez.todoapp.domain.model.ToDoTask


sealed class ToDoListState {
    data object Loading : ToDoListState()
    data class Success(val tasks: List<ToDoTask>) : ToDoListState()
}