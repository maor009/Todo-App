package com.example.todo.screens.todo_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.data.room.Todo

//TodoItem is the interface for how the items will appear on screen

@Composable
fun TodoItem(
    todo: Todo,
    onClick: () -> Unit = {}
) {
    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .padding(5.dp)
        .shadow(
            elevation = 50.dp,
            shape = RoundedCornerShape(5)
        )
        .background(color = Color.White)
        .clickable { onClick() }
        .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center


    ){
        Column(modifier = Modifier.padding(start = 2.dp, top = 20.dp)){
            Text(text = todo.title,
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 10.dp))

            //Row for the Priority and Completed text
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Text(text = "Priority: ${todo.priority}",
                    fontSize = 12.sp,
                    color =  Color.Black
                )

                Text(text = "Completed: ",
                    fontSize = 12.sp,
                    color = Color.Black
                )
                Checkbox(
                    checked = todo.completed,
                    onCheckedChange = null,
                    modifier = Modifier.size(24.dp))
            }

        }
    }
}