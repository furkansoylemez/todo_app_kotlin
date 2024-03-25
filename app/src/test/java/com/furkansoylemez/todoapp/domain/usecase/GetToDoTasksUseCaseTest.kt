package com.furkansoylemez.todoapp.domain.usecase

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
class GetToDoTasksUseCaseTest {

    @Mock
    private lateinit var toDoRepository: ToDoRepository

    private lateinit var getToDoTasksUseCase: GetToDoTasksUseCase

    @Before
    fun setUp() {
        getToDoTasksUseCase = GetToDoTasksUseCase(toDoRepository)
    }

    @Test
    fun `invoke calls repository getAllTasks method`() = runTest {
        // Arrange
        `when`(toDoRepository.getAllTasks()).thenReturn(mock())

        // Act
        getToDoTasksUseCase()

        // Assert
        verify(toDoRepository).getAllTasks()
    }
}