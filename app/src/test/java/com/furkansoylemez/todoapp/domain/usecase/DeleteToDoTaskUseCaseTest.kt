package com.furkansoylemez.todoapp.domain.usecase

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
class DeleteToDoTaskUseCaseTest {

    @Mock
    private lateinit var toDoRepository: ToDoRepository

    private lateinit var deleteToDoTaskUseCase: DeleteToDoTaskUseCase

    @Before
    fun setUp() {
        deleteToDoTaskUseCase = DeleteToDoTaskUseCase(toDoRepository)
    }

    @Test
    fun `invoke calls repository deleteTask method`() = runTest {
        // Arrange
        val toDoTask = ToDoTask(1, "Sample", "Sample Description", false)
        `when`(toDoRepository.deleteTask(toDoTask)).thenReturn(mock())

        // Act
        deleteToDoTaskUseCase(toDoTask)

        // Assert
        verify(toDoRepository).deleteTask(toDoTask)
    }
}