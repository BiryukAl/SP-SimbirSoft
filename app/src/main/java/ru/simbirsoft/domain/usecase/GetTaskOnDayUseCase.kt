package ru.simbirsoft.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.simbirsoft.domain.model.Task
import ru.simbirsoft.domain.repository.TaskRepository
import java.sql.Timestamp

class GetTaskOnDayUseCase(
    private val repository: TaskRepository,
) {

    operator fun invoke(date: Timestamp): Flow<List<Task>> {
        return repository.findSectionDataStart(date, Timestamp(date.time + DAY))
    }

    companion object {
        // IN a timestamp
        private const val MINUTE: Long = 60_000
        private const val HOUR: Long = MINUTE * 60
        const val DAY: Long = HOUR * 24
    }
}
