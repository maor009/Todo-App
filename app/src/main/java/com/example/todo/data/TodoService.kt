package com.example.todo.data

import com.example.todo.data.room.Todo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

//Interface that performs http requests to the API
interface TodoService {

    @GET("api/todos")
    suspend fun getAllTodos(): Response<List<Todo>>

    @GET("api/todos/{id}")
    suspend fun getTodoById(
        @Path("id") id: Int
    ): Response<Todo>
}