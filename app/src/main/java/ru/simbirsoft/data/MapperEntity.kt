package ru.simbirsoft.data

import ru.simbirsoft.data.cache.entities.TaskEntity
import ru.simbirsoft.domain.model.Task
import java.sql.Timestamp

class MapperEntity() {
    fun toTask(taskEntity: TaskEntity): Task {
        with(taskEntity) {
            return Task(
                id = id,
                dateStart = Timestamp(dateStart),
                dateFinish = Timestamp(dateFinish),
                name = name,
                description = description
            )
        }
    }

    fun toTaskEntity(task: Task): TaskEntity {
        with(task) {
            return TaskEntity(
                id = id,
                dateStart = dateStart.time,
                dateFinish = dateFinish.time,
                name = name,
                description = description
            )
        }
    }
}
