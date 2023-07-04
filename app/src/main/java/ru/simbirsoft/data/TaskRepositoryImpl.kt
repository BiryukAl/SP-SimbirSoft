package ru.simbirsoft.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ru.simbirsoft.data.cache.TaskDatabase
import ru.simbirsoft.domain.model.Task
import ru.simbirsoft.domain.repository.TaskRepository

class TaskRepositoryImpl(
    private val local: TaskDatabase,
    private val mapper: MapperEntity,
) : TaskRepository {

    private val taskDao get() = local.taskDao
    override suspend fun add(task: Task): Flow<Boolean> = flow {
        val result = taskDao.add(mapper.toTaskEntity(task))
        emit(result != 0L)
    }

    override fun findAll(): Flow<List<Task>> {
        return taskDao.findAll().map { it.map(mapper::toTask) }
    }

    override suspend fun addFromExternal(): Flow<Boolean> = flow {
        TODO("Not yet implemented")
    }
}
