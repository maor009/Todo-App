package com.example.todo.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TodoDao {

    //Fetch all todos, return a list of todos
    @Query("SELECT * FROM Todo")
    suspend fun getTodos(): List<Todo>

    //Adding todos
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTodos(todo: List<Todo>)

    // Adding new Todos
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewTodo(todos: Todo)

    //Fetch todos based on Id
    @Query("SELECT * FROM Todo WHERE :id = id")
    suspend fun getTodoById(id: Int): Todo

    //Fetch todos based of a list of id's
    @Query("SELECT * FROM Todo WHERE id IN (:ids)")
    suspend fun getTodoByIds(ids: List<Int>): List<Todo>

    // Get the maximum ID of existing Todos
    @Query("SELECT MAX(id) FROM Todo")
    suspend fun getMaxTodoId(): Int?

}