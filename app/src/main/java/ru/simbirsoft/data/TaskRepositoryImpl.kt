package ru.simbirsoft.data

import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.simbirsoft.data.cache.TaskDatabase
import ru.simbirsoft.data.external.model.TasksExternal
import ru.simbirsoft.domain.model.Task
import ru.simbirsoft.domain.repository.TaskRepository
import java.io.FileDescriptor
import java.io.FileReader
import java.sql.Timestamp

class TaskRepositoryImpl(
    private val local: TaskDatabase,
    private val mapper: MapperEntity,
    private val gson: Gson,
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

    override suspend fun addFromExternal(fileDescriptor: FileDescriptor?) {
        val tasks = gson.fromJson(FileReader(fileDescriptor), TasksExternal::class.java)
        tasks.forEach {
            taskDao.add(mapper.toTaskEntity(it))
        }
    }
}
