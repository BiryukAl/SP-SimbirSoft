package ru.simbirsoft.presentation.screens.addTask

import ru.simbirsoft.di.locateLazy
import ru.simbirsoft.domain.usecase.AddTaskUseCase
import ru.simbirsoft.presentation.base.BaseViewModel

class AddTaskViewModel : BaseViewModel() {

    private val addTaskUseCase: AddTaskUseCase by locateLazy()
}
