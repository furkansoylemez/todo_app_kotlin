package com.furkansoylemez.todoapp.domain.model

data class ToDoTask(
    val id: Int = 0,
    val title: String,
    val description: String,
    val isCompleted: Boolean
)