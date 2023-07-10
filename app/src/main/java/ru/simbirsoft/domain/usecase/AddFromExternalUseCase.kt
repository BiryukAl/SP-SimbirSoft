package ru.simbirsoft.domain.usecase

import ru.simbirsoft.domain.repository.TaskRepository
import java.io.FileDescriptor

class AddFromExternalUseCase(
    private val repository: TaskRepository,
) {
    suspend operator fun invoke(fileDescriptor: FileDescriptor?) {
        repository.addFromExternal(fileDescriptor)
    }
}
