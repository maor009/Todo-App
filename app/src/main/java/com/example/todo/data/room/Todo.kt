package com.example.todo.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

//Information about the different todos from dummy API

@Entity
data class Todo(
    @PrimaryKey
    val id: Int,
    val title: String,
    val priority: String,
    val completed: Boolean
)