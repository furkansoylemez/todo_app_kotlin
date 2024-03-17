package com.furkansoylemez.todoapp.data.repository

import com.furkansoylemez.todoapp.data.dao.ToDoDao
import com.furkansoylemez.todoapp.domain.mapper.ToDoTaskMapper
import com.furkansoylemez.todoapp.domain.model.ToDoTask
import com.furkansoylemez.todoapp.domain.repository.ToDoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ToDoRepositoryImpl @Inject constructor(
    private val toDoDao: ToDoDao
): ToDoRepository {

    override fun getAllTasks(): Flow<List<ToDoTask>> =
        toDoDao.getAllItems().map { entities ->
            entities.map { entity ->
                ToDoTaskMapper.toDomainModel(entity)
            }
        }

    override suspend fun insertTask(toDoTask: ToDoTask) {
        val toDoItem = ToDoTaskMapper.toDataModel(toDoTask)
        toDoDao.insertItem(toDoItem)
    }

    override suspend fun updateTask(toDoTask: ToDoTask) {
        val toDoItem = ToDoTaskMapper.toDataModel(toDoTask)
        toDoDao.updateItem(toDoItem)
    }

    override suspend fun deleteTask(toDoTask: ToDoTask) {
        toDoDao.deleteItemById(toDoTask.id)
    }
}