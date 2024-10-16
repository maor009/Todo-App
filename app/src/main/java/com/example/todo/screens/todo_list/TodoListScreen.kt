package com.example.todo.screens.todo_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// TodoListScreen: interface for the home screen, shows all todos

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    navController: NavController,
    viewModel: TodoListViewModel,
    todoClick: (todoId: Int) -> Unit = {}
){
    val todos = viewModel.todos.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    //Header with nav
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.End,
        ) {
            //Header text
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 5.dp),
                text = "Todos",
                fontSize = 25.sp,
            )
            //Icon and nav for adding todos
            IconButton(onClick = { navController.navigate("addNewScreen") }) {
                Icon(
                    imageVector = Icons.Rounded.AddCircle,
                    contentDescription = "Add todos"
                )

            }
        }

        //TextField for search bar
        TextField(
            value = searchQuery,
            onValueChange = { viewModel.setSearchQuery(it) },
            label = { Text("Search Todo") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        //LazyColum with if else statement to display the todos list and filter search
        LazyColumn {
            val itemsToDisplay = if (searchQuery.isNotEmpty()) {
                viewModel.filteredTodos()
            } else {
                todos.value
            }

            items(itemsToDisplay) { todo ->
                TodoItem(todo = todo, onClick = { todoClick(todo.id) })
            }
        }
    }
}