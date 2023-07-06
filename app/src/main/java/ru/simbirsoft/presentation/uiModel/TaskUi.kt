package ru.simbirsoft.presentation.uiModel

sealed class TaskUi(val id: Int) {
    data class Task(
        val dateStart: String,
        val dateFinish: String,
        val name: String,
        val description: String,
    ) : TaskUi(0)

    data class SeparatorHour(
        val hour: Int
    ) : TaskUi(0)
}
