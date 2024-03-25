package com.furkansoylemez.todoapp.data.repository

import com.furkansoylemez.todoapp.data.dao.ToDoDao
import com.furkansoylemez.todoapp.data.model.ToDoItem
import com.furkansoylemez.todoapp.domain.mapper.ToDoTaskMapper
import com.furkansoylemez.todoapp.domain.model.ToDoTask
import com.furkansoylemez.todoapp.domain.repository.ToDoRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ToDoRepositoryImplTest {

    @Mock
    private lateinit var toDoDao: ToDoDao

    private lateinit var toDoRepository: ToDoRepository

    private lateinit var toDoTask: ToDoTask
    private lateinit var toDoItem: ToDoItem

    @Before
    fun setUp() {
        toDoRepository = ToDoRepositoryImpl(toDoDao)
        toDoTask = ToDoTask(1, "Sample", "Sample Description", false)
        toDoItem = ToDoTaskMapper.toDataModel(toDoTask)
    }

    @Test
    fun `getAllTasks calls dao getAllItems method`() = runTest {
        // Arrange
        `when`(toDoDao.getAllItems()).thenReturn(mock())

        // Act
        toDoRepository.getAllTasks()

        // Assert
        verify(toDoDao).getAllItems()
    }

    @Test
    fun `insertTask calls dao insertItem method`() = runTest {
        // Arrange
        `when`(toDoDao.insertItem(toDoItem)).thenReturn(mock())

        // Act
        toDoRepository.insertTask(toDoTask)

        // Assert
        verify(toDoDao).insertItem(toDoItem)
    }

    @Test
    fun `updateTask calls dao updateItem method`() = runTest {
        // Act
        toDoRepository.updateTask(toDoTask)

        // Assert
        verify(toDoDao).updateItem(toDoItem)
    }

    @Test
    fun `deleteTask calls dao deleteItemById method`() = runTest {
        // Act
        toDoRepository.deleteTask(toDoTask)

        // Assert
        verify(toDoDao).deleteItemById(toDoTask.id)
    }
}