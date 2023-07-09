package ru.simbirsoft.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ru.simbirsoft.data.cache.TaskDatabase
import ru.simbirsoft.domain.model.Task
import ru.simbirsoft.domain.repository.TaskRepository
import java.sql.Timestamp

class TaskRepositoryImpl(
    private val local: TaskDatabase,
    private val mapper: MapperEntity,
) : TaskRepository {

    private val taskDao get() = local.taskDao
    override suspend fun add(task: Task) {
        taskDao.add(mapper.toTaskEntity(task))
    }

    override fun findAll(): Flow<List<Task>> {
        return taskDao.findAll().map { it.map(mapper::toTask) }
    }

    override fun findSectionDataStart(
        fromDataStart: Timestamp,
        toDataStart: Timestamp
    ): Flow<List<Task>> {
        val from = fromDataStart.time
        val to = toDataStart.time
        return taskDao.findOnSectionDataStart(from, to).map { it.map(mapper::toTask) }
    }

    override suspend fun addFromExternal() {
        TODO("Not yet implemented")
    }
}
