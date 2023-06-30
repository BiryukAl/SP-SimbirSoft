package ru.simbirsoft.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.simbirsoft.domain.model.Task

interface TaskRepository {
    suspend fun add(task: Task): Flow<Boolean>
    suspend fun findAll(): Flow<Task>
    suspend fun addFromExternal(): Flow<Boolean>
}
