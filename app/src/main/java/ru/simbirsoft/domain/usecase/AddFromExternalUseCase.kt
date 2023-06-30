package ru.simbirsoft.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.simbirsoft.domain.repository.TaskRepository

class AddFromExternalUseCase(
    private val repository: TaskRepository,
) {
    suspend operator fun invoke(): Flow<Boolean> {
        return repository.addFromExternal()
    }
}
