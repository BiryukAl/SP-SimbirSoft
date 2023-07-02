package ru.simbirsoft.presentation.screens.allTask

import ru.simbirsoft.di.locateLazy
import ru.simbirsoft.domain.usecase.AddTaskUseCase
import ru.simbirsoft.presentation.base.BaseViewModel

class AllTaskViewModel : BaseViewModel() {

    private val addTaskUseCase: AddTaskUseCase by locateLazy()



}
