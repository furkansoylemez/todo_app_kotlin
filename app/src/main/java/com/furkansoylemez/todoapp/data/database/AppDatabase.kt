package com.furkansoylemez.todoapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.furkansoylemez.todoapp.data.dao.ToDoDao
import com.furkansoylemez.todoapp.data.model.ToDoItem

@Database(entities = [ToDoItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun toDoDao(): ToDoDao
}