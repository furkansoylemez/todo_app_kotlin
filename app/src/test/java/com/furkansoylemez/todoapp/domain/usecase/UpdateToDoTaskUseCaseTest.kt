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
class UpdateToDoTaskUseCaseTest {

    @Mock
    private lateinit var toDoRepository: ToDoRepository

    private lateinit var updateToDoTaskUseCase: UpdateToDoTaskUseCase

    @Before
    fun setUp() {
        updateToDoTaskUseCase = UpdateToDoTaskUseCase(toDoRepository)
    }

    @Test
    fun `invoke calls repository updateTask method`() = runTest {
        // Arrange
        val toDoTask = ToDoTask(1, "Sample", "Sample Description", false)
        `when`(toDoRepository.updateTask(toDoTask)).thenReturn(mock())

        // Act
        updateToDoTaskUseCase(toDoTask)

        // Assert
        verify(toDoRepository).updateTask(toDoTask)
    }
}