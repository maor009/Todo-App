package com.example.todo.screens.add_new

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.TodoRepository
import com.example.todo.data.room.Todo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddNewViewModel : ViewModel(){

    //Fetching the todos from the todolist
    private val _todo = MutableStateFlow<List<Todo>>(emptyList())
    val todo: StateFlow<List<Todo>> = _todo

    //Initializing the existingId by fetching the max id from the repository
    private var existingId = 0
    fun initializeExistingId() {
        viewModelScope.launch {
            existingId = TodoRepository.getTodoMaxId()
        }
    }
    //Adding the new todos to the local storage
    suspend fun addNewTodo(
        title: String,
        priority: String,
        completed: Boolean
    ) {
        val existingId = TodoRepository.getTodoMaxId()
        val newTodo = Todo(
            id = existingId + 1,
            title = title,
            priority = priority,
            completed = completed
        )

        TodoRepository.addNewTodo(newTodo)

        refreshTodos()
    }


    //Refreshing todos and error handling
    private suspend fun refreshTodos() {
        try {
            val todos = TodoRepository.getTodos()
            _todo.value = todos
        } catch (e: Exception) {
            Log.e("AddNewViewModel", "Error refreshing Todos", e)
        }
    }

}