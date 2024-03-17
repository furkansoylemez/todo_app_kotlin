package com.furkansoylemez.todoapp.domain.usecase

import com.furkansoylemez.todoapp.domain.model.ToDoTask
import com.furkansoylemez.todoapp.domain.repository.ToDoRepository
import javax.inject.Inject

class DeleteToDoTaskUseCase @Inject constructor(
    private val toDoRepository: ToDoRepository
) {
    suspend operator fun invoke(toDoTask: ToDoTask) {
        toDoRepository.deleteTask(toDoTask)
    }
}