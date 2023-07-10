package ru.simbirsoft.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.simbirsoft.domain.model.Task
import java.io.FileDescriptor
import java.sql.Timestamp

interface TaskRepository {
    suspend fun add(task: Task)
    fun findAll(): Flow<List<Task>>
    suspend fun addFromExternal(fileDescriptor: FileDescriptor?)
    fun findSectionDataStart(fromDataStart: Timestamp, toDataStart: Timestamp): Flow<List<Task>>
}
