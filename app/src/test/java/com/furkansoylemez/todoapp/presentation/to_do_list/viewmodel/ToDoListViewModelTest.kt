package com.furkansoylemez.todoapp.presentation.to_do_list.viewmodel

import app.cash.turbine.test
import com.furkansoylemez.todoapp.domain.model.ToDoTask
import com.furkansoylemez.todoapp.domain.repository.ToDoRepository
import com.furkansoylemez.todoapp.domain.usecase.AddToDoTaskUseCase
import com.furkansoylemez.todoapp.domain.usecase.DeleteToDoTaskUseCase
import com.furkansoylemez.todoapp.domain.usecase.GetToDoTasksUseCase
import com.furkansoylemez.todoapp.domain.usecase.UpdateToDoTaskUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class ToDoListViewModelTest {

    @get:Rule
    val mainRule = MainDispatcherRule(UnconfinedTestDispatcher())

    @Mock
    lateinit var getToDoTasksUseCase: GetToDoTasksUseCase

    @Mock
    lateinit var addToDoTaskUseCase: AddToDoTaskUseCase

    @Mock
    lateinit var deleteToDoTaskUseCase: DeleteToDoTaskUseCase

    @Mock
    lateinit var updateToDoTaskUseCase: UpdateToDoTaskUseCase

    private lateinit var viewModel: ToDoListViewModel

    @Test
    fun `Should set UI state successfully`() = runTest {
        val testRepository = TestRepository()
        `when`(getToDoTasksUseCase()).thenReturn(testRepository.getAllTasks())

        viewModel = ToDoListViewModel(
            getToDoTasksUseCase,
            addToDoTaskUseCase,
            updateToDoTaskUseCase,
            deleteToDoTaskUseCase
        )
        verify(getToDoTasksUseCase).invoke()
        viewModel.uiState.test {
            val loading = awaitItem()
            assertTrue("uiState is type of ToDoListState.Loading", loading is ToDoListState.Loading)
            val tasks = listOf(ToDoTask(2,"Test","Test",false))
            testRepository.emit(tasks)

            val successItem = awaitItem()
            assertTrue("successItem is type of MessageUiState.Success", successItem is ToDoListState.Success)
            assertEquals((successItem as ToDoListState.Success).tasks, tasks)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `addTask calls addToDoTaskUseCase`() = runTest {
        viewModel = ToDoListViewModel(
            getToDoTasksUseCase,
            addToDoTaskUseCase,
            updateToDoTaskUseCase,
            deleteToDoTaskUseCase
        )

        val task = ToDoTask(title = "Test", description = "Test", isCompleted = false)

        // Arrange
        `when`(addToDoTaskUseCase.invoke(task)).thenReturn(mock())

        // Act

        viewModel.addTask(task.title,task.description)

        // Assert
        verify(addToDoTaskUseCase).invoke(task)
    }

    @Test
    fun `deleteTask calls deleteToDoTaskUseCase`() = runTest {
        viewModel = ToDoListViewModel(
            getToDoTasksUseCase,
            addToDoTaskUseCase,
            updateToDoTaskUseCase,
            deleteToDoTaskUseCase
        )

        val task = ToDoTask(id = 2, title = "Test", description = "Test", isCompleted = false)

        // Arrange
        `when`(deleteToDoTaskUseCase.invoke(task)).thenReturn(mock())

        // Act

        viewModel.deleteTask(task)

        // Assert
        verify(deleteToDoTaskUseCase).invoke(task)
    }

    @Test
    fun `updateTask calls updateToDoTaskUseCase`() = runTest {
        viewModel = ToDoListViewModel(
            getToDoTasksUseCase,
            addToDoTaskUseCase,
            updateToDoTaskUseCase,
            deleteToDoTaskUseCase
        )

        val task = ToDoTask(id = 2, title = "Test", description = "Test", isCompleted = false)

        // Arrange
        `when`(updateToDoTaskUseCase.invoke(task)).thenReturn(mock())

        // Act

        viewModel.updateTask(task)

        // Assert
        verify(updateToDoTaskUseCase).invoke(task)
    }

    class TestRepository : ToDoRepository {
        private val flow = MutableSharedFlow<List<ToDoTask>>()

        suspend fun emit(value: List<ToDoTask>) {
            flow.emit(value)
        }

        override fun getAllTasks(): Flow<List<ToDoTask>> {
            return flow
        }

        override suspend fun insertTask(toDoTask: ToDoTask) {
        }

        override suspend fun updateTask(toDoTask: ToDoTask) {
        }

        override suspend fun deleteTask(toDoTask: ToDoTask) {
        }
    }

    class MainDispatcherRule(
        private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
    ) : TestWatcher() {
        override fun starting(description: Description) {
            Dispatchers.setMain(testDispatcher)
        }

        override fun finished(description: Description) {
            Dispatchers.resetMain()
        }
    }
}


