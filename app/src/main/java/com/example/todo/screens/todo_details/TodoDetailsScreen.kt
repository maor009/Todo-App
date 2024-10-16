package com.example.todo.screens.todo_details


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//TodoDetailsScreen: Interface for details about a todoo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDetailsScreen(
    viewModel: TodoDetailsViewModel,
    navController: NavController,
    onArrowButtonClick: () -> Unit = {}
) {
    val todoState = viewModel.selectedTodo.collectAsState()
    val todo = todoState.value
    val context = LocalContext.current

    //Variables for completing a task and a pop up message
    var isTaskCompleted by remember {mutableStateOf(todo?.completed ?: false)}
    var snackbarVisible by remember {mutableStateOf(false)}

    //Header with nav
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start
        ) {
            //Arrow icon to navigate back to home screen
            IconButton(onClick = { onArrowButtonClick() },
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "Arrow Back"
                )
            }

            Text(
                modifier = Modifier.padding(top = 5.dp),
                text = "Todo Details",
                fontSize = 25.sp
            )
        }
    }

    //Column for the actual detail screen
            Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 60.dp)
                .background(Color.DarkGray)

        ) {
            Text(
                modifier = Modifier
                    .padding(4.dp)
                    .padding(top = 10.dp),
                text = todo?.title ?: "Todo Title",
                fontSize = 25.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
            )

            Row(
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                Text(
                    text = "Priority: ${todo?.priority}",
                    fontSize = 20.sp,
                    color = Color.Gray
                )
            }

            Row(
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                Text(
                    text = "Completed: ${todo?.completed}",
                    fontSize = 20.sp,
                    color = Color.Gray
                )
            }

            //Button for completing a task
            Button(
                onClick = {
                    isTaskCompleted = !isTaskCompleted
                    snackbarVisible = true
                },
                modifier = Modifier
                    .width(150.dp)
                    .padding(top = 50.dp)
                    .align(Alignment.CenterHorizontally)
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(50)
                    )
                    .background(
                        color = Color.White),

                colors = ButtonDefaults.textButtonColors(
                    contentColor = Color.Blue
                )

            ) {
                Text(if (isTaskCompleted) "Completed" else "Complete",
                    fontSize = 20.sp)
            }

            if (snackbarVisible) {
                DisposableEffect(Unit){
                    onDispose {
                        viewModel.viewModelScope.launch {
                            delay(2000)
                            snackbarVisible = false
                        }
                    }
                }
                Snackbar (
                    content = { Text("The task was completed!")},
                    modifier = Modifier.padding(top = 16.dp)
                )

            }

        }
    }

