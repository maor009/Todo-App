package com.example.todo.screens.todo_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.TodoRepository
import com.example.todo.data.room.Todo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale

class TodoListViewModel : ViewModel() {

    //List of the todos
    private val _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos: StateFlow<List<Todo>> = _todos.asStateFlow()

    //Implementing searchQuery
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        loadTodos()
    }

    //Loading the todos with error handling
    private fun loadTodos() {
        viewModelScope.launch {
            try {
                val loadedTodos = TodoRepository.getTodos()
                _todos.value = loadedTodos
            } catch (e: Exception) {
                Log.e("TodoListViewModel", "Error loading Todos", e)
            }
        }
    }

    //Function for calling updated list
    fun refreshTodos() {
        viewModelScope.launch {
            _todos.value = TodoRepository.getTodos()
        }
    }

    //Search function
    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    //Function for fetching filtered todos based on searchQuery
    fun filteredTodos(): List<Todo> {
        val query = searchQuery.value.lowercase(Locale.getDefault())
        return if (query.isBlank()) {
            todos.value
        } else {
            todos.value.filter { it.title.lowercase(Locale.getDefault()).contains(query) }
        }
    }
}
