package com.example.todo.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Todo::class], version = 37, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}