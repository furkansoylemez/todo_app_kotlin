package com.furkansoylemez.todoapp.di

import android.content.Context
import androidx.room.Room
import com.furkansoylemez.todoapp.data.dao.ToDoDao
import com.furkansoylemez.todoapp.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "todo_database")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideToDoDao(appDatabase: AppDatabase): ToDoDao = appDatabase.toDoDao()
}