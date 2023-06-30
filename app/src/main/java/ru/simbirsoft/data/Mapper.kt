package ru.simbirsoft.data

import ru.simbirsoft.data.cache.entities.TaskEntity
import ru.simbirsoft.domain.model.Task

fun TaskEntity.toTask(): Task {
    return Task(
        id = id,
        dateStart = dateStart,
        dateFinish = dateFinish,
        name = name,
        description = description
    )
}

fun Task.toTaskEntity(): TaskEntity {
    return TaskEntity(
        id = id,
        dateStart = dateStart,
        dateFinish = dateFinish,
        name = name,
        description = description
    )
}
