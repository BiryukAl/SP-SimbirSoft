package ru.simbirsoft.data.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.simbirsoft.data.cache.entities.TaskEntity

@Dao
interface TaskDao {

    @Insert
    suspend fun add(task: TaskEntity):Long

    @Query("SELECT * FROM task")
    fun findAll(): Flow<List<TaskEntity>>

}
