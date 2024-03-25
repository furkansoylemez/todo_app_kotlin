package com.furkansoylemez.todoapp.domain.mapper

import com.furkansoylemez.todoapp.data.model.ToDoItem
import com.furkansoylemez.todoapp.domain.model.ToDoTask
import org.junit.Assert.assertEquals
import org.junit.Test
class ToDoTaskMapperTest {

    @Test
    fun `toDomainModel converts ToDoItem to ToDoTask accurately`() {
        // Arrange
        val todoItem = ToDoItem(
            id = 1,
            title = "Test Task",
            description = "Test Description",
            isCompleted = true
        )

        // Act
        val todoTask = ToDoTaskMapper.toDomainModel(todoItem)

        // Assert
        assertEquals(todoItem.id, todoTask.id)
        assertEquals(todoItem.title, todoTask.title)
        assertEquals(todoItem.description, todoTask.description)
        assertEquals(todoItem.isCompleted, todoTask.isCompleted)
    }

    @Test
    fun `toDataModel converts ToDoTask to ToDoItem accurately`() {
        // Arrange
        val todoTask = ToDoTask(
            id = 1,
            title = "Test Task",
            description = "Test Description",
            isCompleted = true
        )

        // Act
        val todoItem = ToDoTaskMapper.toDataModel(todoTask)

        // Assert
        assertEquals(todoTask.id, todoItem.id)
        assertEquals(todoTask.title, todoItem.title)
        assertEquals(todoTask.description, todoItem.description)
        assertEquals(todoTask.isCompleted, todoItem.isCompleted)
    }
}