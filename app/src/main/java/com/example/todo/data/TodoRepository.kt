package com.example.todo.data

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.todo.data.room.AppDatabase
import com.example.todo.data.room.Todo
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TodoRepository {

    //HttpClient
    private val _httpClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .build()

    //Retrofit client
    private val _retrofit = Retrofit.Builder()
        .client(_httpClient)
        .baseUrl("https://dummyapi.online/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    //Fetching data from the API
    private val _todoService = _retrofit.create(TodoService::class.java)

    //Communication with the database
    private lateinit var _appDatabase: AppDatabase
    private val _todoDao by lazy { _appDatabase.todoDao() }

    fun initiateAppDatabase (context: Context) {
        _appDatabase = Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "appDatabase"
        ).fallbackToDestructiveMigration().build()
    }

    //Function that tests if the adding operation was successful
    suspend fun addTodo(todo: Todo): List<Todo> {
        return try {
            _todoDao.addTodos(listOf(todo))
            _todoDao.getTodos()
        } catch (e: Exception) {
            Log.e("TodoRepository", "Error adding Todos", e)
            _todoDao.getTodos()
        }
    }

    //Function that tests adding todos from API and adding to the AppDatabase
    suspend fun getTodos(): List<Todo> {
        return try {
            val response = _todoService.getAllTodos()
            if (response.isSuccessful) {
                val todo = response.body() ?: emptyList()
                _todoDao.addTodos(todo)
                _todoDao.getTodos()
            } else {
                throw Exception("getTodo is not working")
            }
        } catch (e: Exception) {
            Log.e("TodoRespitory", "Error")
            _todoDao.getTodos()
        }
    }

    //Fetching todos based on id and adding to the AppDatabase
    suspend fun getTodoById(id: Int): Todo {
        return try {
            val response = _todoService.getTodoById(id)
            if (response.isSuccessful) {
                _todoDao.getTodoById(id)
                return _todoDao.getTodoById(id)
            } else {
                throw Exception("getProductById is not working")
            }
        } catch (e: Exception){
            Log.e("TodoRepository", "Error", e)
            _todoDao.getTodoById(id)
        }
    }

    //Function for adding new todos
    suspend fun addNewTodo(todos: Todo) {
        try {
            _todoDao.addNewTodo(todos)
        } catch (e: Exception) {
            Log.e("TodoRepository", "Error adding AddTodo", e)
        }
    }

    suspend fun getTodoByIds(ids: List<Int>): List<Todo> {
        return _todoDao.getTodoByIds(ids)
    }

    suspend fun getTodoMaxId(): Int {
        return _todoDao.getMaxTodoId() ?: 0
    }

}