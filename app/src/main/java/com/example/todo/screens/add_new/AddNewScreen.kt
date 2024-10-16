package com.example.todo.screens.add_new

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewScreen(
    viewModel: AddNewViewModel,
    navController: NavController,
    onArrowButtonClick: () -> Unit = {}
) {

    var title by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf("") }
    var completed by remember { mutableStateOf(false) }

    //Header with nav
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .background(Color.White),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start
        ) {
            //Arrow icon to navigate back to home screen
            IconButton(
                onClick =
                { onArrowButtonClick()
                    navController.navigate("todoListScreen")
                }) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "Arrow Back"
                )
            }
            Text(
                modifier = Modifier.padding(top = 5.dp),
                text = "New Todo",
                fontSize = 25.sp
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 60.dp)
    ) {

        //Adding textfield for title
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Todo Title") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)

        )

        //Row for adding the text and radiobuttons to priority
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Text("Priority: ")

            Spacer(modifier = Modifier.width(2.dp))

            Text("Low")
            RadioButton(
                selected = priority == "Low",
                onClick = { priority= "Low" },
                modifier = Modifier.padding(end = 2.dp)
            )

            Spacer(modifier = Modifier.width(2.dp))

            Text("Medium")
            RadioButton(
                selected = priority == "Medium",
                onClick = { priority = "Medium" },
                modifier = Modifier.padding(end = 2.dp)
            )

            Spacer(modifier = Modifier.width(2.dp))

            Text("High")
            RadioButton(
                selected = priority == "High",
                onClick = { priority = "High" },
                modifier = Modifier.padding(end = 2.dp)
            )
        }

        //Row for adding the text and switch for Completed
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Text("Completed: ")
            Switch(
                checked = completed,
                onCheckedChange = { completed = it },
                modifier = Modifier
                    .padding(8.dp)
            )
        }

        //Button for adding the new task
        Button(
            onClick = {
                viewModel.viewModelScope.launch {
                    viewModel.addNewTodo(
                        title = title,
                        priority = priority,
                        completed = completed
                    )

                    navController.popBackStack()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Add new Todo")
        }
    }
}