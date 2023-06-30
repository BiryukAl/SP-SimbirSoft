package ru.simbirsoft.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ru.simbirsoft.data.cache.dao.TaskDao
import ru.simbirsoft.domain.model.Task
import ru.simbirsoft.domain.repository.TaskRepository

class TaskRepositoryImpl(
    private val local: TaskDao,
    private val externalStorage: Any,
) : TaskRepository {
    override suspend fun add(task: Task): Flow<Boolean> = flow {
        val result = local.add(task.toTaskEntity())
        emit(result != 0)
    }

    override suspend fun findAll(): Flow<Task> {
        return local.findAll().map { it.toTask() }
    }

    override suspend fun addFromExternal(): Flow<Boolean> = flow {
        TODO("Not yet implemented")
    }
}
