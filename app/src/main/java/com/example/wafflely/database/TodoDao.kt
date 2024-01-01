package com.example.wafflely.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.wafflely.model.TodoInfo

@Dao
interface TodoDao {
    @Insert
    fun insertTodoData(TodoInfo: TodoInfo)

    @Update
    fun updateTodoData(TodoInfo: TodoInfo)

    @Delete
    fun deleteTodoData(TodoInfo: TodoInfo)

    @Query("SELECT * FROM TodoInfo")
    fun getAllReadData(): List<TodoInfo>
}