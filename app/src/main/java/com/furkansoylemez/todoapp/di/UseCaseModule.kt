package com.furkansoylemez.todoapp.di

import com.furkansoylemez.todoapp.domain.repository.ToDoRepository
import com.furkansoylemez.todoapp.domain.usecase.AddToDoTaskUseCase
import com.furkansoylemez.todoapp.domain.usecase.DeleteToDoTaskUseCase
import com.furkansoylemez.todoapp.domain.usecase.GetToDoTasksUseCase
import com.furkansoylemez.todoapp.domain.usecase.UpdateToDoTaskUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetToDoTasksUseCase(toDoRepository: ToDoRepository): GetToDoTasksUseCase =
        GetToDoTasksUseCase(toDoRepository)

    @Provides
    fun provideAddToDoTaskUseCase(toDoRepository: ToDoRepository): AddToDoTaskUseCase =
        AddToDoTaskUseCase(toDoRepository)

    @Provides
    fun provideUpdateToDoTaskUseCase(toDoRepository: ToDoRepository): UpdateToDoTaskUseCase =
        UpdateToDoTaskUseCase(toDoRepository)

    @Provides
    fun provideDeleteToDoTaskUseCase(toDoRepository: ToDoRepository): DeleteToDoTaskUseCase =
        DeleteToDoTaskUseCase(toDoRepository)
}