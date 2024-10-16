package com.example.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todo.data.TodoRepository
import com.example.todo.screens.add_new.AddNewScreen
import com.example.todo.screens.add_new.AddNewViewModel
import com.example.todo.screens.todo_details.TodoDetailsScreen
import com.example.todo.screens.todo_details.TodoDetailsViewModel
import com.example.todo.screens.todo_list.TodoListScreen
import com.example.todo.screens.todo_list.TodoListViewModel
import com.example.todo.ui.theme.TodoTheme

class MainActivity : ComponentActivity() {

    private val _todoListViewModel: TodoListViewModel by viewModels()
    private val _todoDetailsViewModel: TodoDetailsViewModel by viewModels()
    private val _addNewViewModel: AddNewViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Communicating with the AppDatabase
        TodoRepository.initiateAppDatabase(applicationContext)

        setContent {
            //Navigation
            val navController = rememberNavController()
            TodoTheme {
                //Homescreen - TodoListScreen
                NavHost(navController = navController, startDestination = "todoListScreen") {
                    composable(route = "todoListScreen") {
                        TodoListScreen(
                            viewModel = _todoListViewModel,
                            //Navigation from Todos to DetailsScreen
                            todoClick = { todoId ->
                                navController.navigate("todoDetailsScreen/$todoId")
                            },
                            navController = navController,
                        )
                    }

                    //Detailed screen for the todos
                    composable(route = "todoDetailsScreen/{todoId}",
                        arguments = listOf(
                            navArgument(name = "todoId") {
                                type = NavType.IntType
                            }
                        )
                    ) { backStackEntry ->
                        val todoId = backStackEntry.arguments?.getInt("todoId") ?: -1
                        LaunchedEffect(todoId) {
                            _todoDetailsViewModel.setSelectedTodo(todoId)
                        }
                        TodoDetailsScreen(
                            viewModel = _todoDetailsViewModel,
                            onArrowButtonClick = { navController.popBackStack() },
                            navController = navController,
                        )
                    }

                    //Screen for adding ned todos
                    composable(route = "addNewScreen") {
                        AddNewScreen(
                            viewModel = _addNewViewModel,
                            onArrowButtonClick = {navController.popBackStack() },
                            navController = navController,
                            )
                    }
                }

            }
        }
    }
}