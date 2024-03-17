package com.furkansoylemez.todoapp.domain.repository

import com.furkansoylemez.todoapp.domain.model.ToDoTask
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {
    fun getAllTasks(): Flow<List<ToDoTask>>
    suspend fun insertTask(toDoTask: ToDoTask)
    suspend fun updateTask(toDoTask: ToDoTask)
    suspend fun deleteTask(toDoTask: ToDoTask)
}