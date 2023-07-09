package ru.simbirsoft.presentation

import ru.simbirsoft.domain.model.Task
import ru.simbirsoft.presentation.uiModel.TaskUi
import ru.simbirsoft.presentation.util.Formatters

class MapperUi(private val formatters: Formatters) {

    fun toTaskUi(task: Task): TaskUi.Task {
        with(task) {
            return TaskUi.Task(
                dateStart = formatters.timeFormatter.format(dateStart),
                dateFinish = formatters.timeFormatter.format(dateFinish),
                name = name,
                description = description,
            )
        }
    }
}
