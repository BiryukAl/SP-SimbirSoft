package ru.simbirsoft.presentation

import ru.simbirsoft.domain.model.Task
import ru.simbirsoft.presentation.uiModel.TaskUi

class MapperUi {
    fun toTaskUi(task: Task): TaskUi.Task {
        with(task){
            return TaskUi.Task(
                dateStart = dateStart.toString(),
                dateFinish = dateFinish.toString(),
                name = name,
                description = description,
            )
        }
    }
}
