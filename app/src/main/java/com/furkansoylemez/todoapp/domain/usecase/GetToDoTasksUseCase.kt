package com.furkansoylemez.todoapp.domain.usecase

import com.furkansoylemez.todoapp.domain.model.ToDoTask
import com.furkansoylemez.todoapp.domain.repository.ToDoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetToDoTasksUseCase @Inject constructor(
    private val toDoRepository: ToDoRepository
) {
    operator fun invoke(): Flow<List<ToDoTask>> = toDoRepository.getAllTasks()
}