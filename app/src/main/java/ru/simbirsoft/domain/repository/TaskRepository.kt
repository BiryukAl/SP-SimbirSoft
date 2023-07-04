package ru.simbirsoft.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.simbirsoft.domain.model.Task
import java.sql.Timestamp

interface TaskRepository {
    suspend fun add(task: Task): Flow<Boolean>
    fun findAll(): Flow<List<Task>>
    suspend fun addFromExternal(): Flow<Boolean>
    fun findSectionDataStart(fromDataStart: Timestamp, toDataStart: Timestamp): Flow<List<Task>>
}
