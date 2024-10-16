package com.example.todo.screens.todo_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.TodoRepository
import com.example.todo.data.room.Todo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TodoDetailsViewModel: ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _selectedTodo = MutableStateFlow<Todo?>(null)
    val selectedTodo = _selectedTodo.asStateFlow()

    private val _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos: StateFlow<List<Todo>> = _todos.asStateFlow()

    //Function for setting the selected task
    fun setSelectedTodo(todoId: Int) {
        viewModelScope.launch {
            _loading.value = true
            _selectedTodo.value = TodoRepository.getTodoById(todoId)
            _loading.value = false
        }
    }
}