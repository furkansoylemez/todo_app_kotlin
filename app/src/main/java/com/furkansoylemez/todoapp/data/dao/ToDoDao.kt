package com.furkansoylemez.todoapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.furkansoylemez.todoapp.data.model.ToDoItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {
    @Query("SELECT * FROM todo_items")
    fun getAllItems(): Flow<List<ToDoItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(toDoItem: ToDoItem)

    @Update
    suspend fun updateItem(toDoItem: ToDoItem)
    @Query("DELETE FROM todo_items WHERE id = :id")
    suspend fun deleteItemById(id: Int)
}