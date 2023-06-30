package ru.simbirsoft.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.simbirsoft.domain.model.Task
import ru.simbirsoft.domain.repository.TaskRepository

class GetAllTaskUseCase(
    private val repository: TaskRepository,
) {
    suspend operator fun invoke(): Flow<Task>{
        return repository.findAll()
    }
}
