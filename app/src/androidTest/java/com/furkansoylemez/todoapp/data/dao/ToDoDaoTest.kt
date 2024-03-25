package com.furkansoylemez.todoapp.data.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.furkansoylemez.todoapp.data.database.AppDatabase
import com.furkansoylemez.todoapp.data.model.ToDoItem
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch


@RunWith(AndroidJUnit4::class)
@SmallTest
class ToDoDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var toDoDao: ToDoDao
    @Before
    fun setupDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        toDoDao = database.toDoDao()
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun insertItem_returnsTrue() = runBlocking {
        val toDoItem = ToDoItem(id = 1, "ToDo Title","ToDo Description",false)
        toDoDao.insertItem(toDoItem)

        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            toDoDao.getAllItems().collect {
                assertThat(it).contains(toDoItem)
                latch.countDown()
            }
        }
        latch.await()
        job.cancelAndJoin()
    }

    @Test
    fun deleteItemById_returnsTrue() = runBlocking {
        val toDoItem = ToDoItem(id = 1, "ToDo Title","ToDo Description",false)
        val secondToDoItem = ToDoItem(id = 2, "ToDo Title2","ToDo Description2",true)
        toDoDao.insertItem(toDoItem)
        toDoDao.insertItem(secondToDoItem)

        toDoDao.deleteItemById(secondToDoItem.id)

        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            toDoDao.getAllItems().collect {
                assertThat(it).contains(toDoItem)
                assertThat(it).doesNotContain(secondToDoItem)
                latch.countDown()
            }
        }
        latch.await()
        job.cancelAndJoin()

    }

    @Test
    fun updateItem_returnsTrue() = runBlocking {
        //insert item
        val toDoItem = ToDoItem(id = 1, "ToDo Title","ToDo Description",false)
        toDoDao.insertItem(toDoItem)

        // create updated item
        val updatedToDo = toDoItem.copy(isCompleted = true)

        // update
        toDoDao.updateItem(updatedToDo)

        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            toDoDao.getAllItems().collect {
                assertThat(it).contains(updatedToDo)
                latch.countDown()
            }
        }
        latch.await()
        job.cancelAndJoin()

    }
}