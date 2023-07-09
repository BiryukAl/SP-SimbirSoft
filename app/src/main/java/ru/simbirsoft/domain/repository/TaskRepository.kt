package ru.simbirsoft.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.simbirsoft.domain.model.Task
import java.sql.Timestamp

interface TaskRepository {
    suspend fun add(task: Task)
    fun findAll(): Flow<List<Task>>
    suspend fun addFromExternal()
    fun findSectionDataStart(fromDataStart: Timestamp, toDataStart: Timestamp): Flow<List<Task>>
}
