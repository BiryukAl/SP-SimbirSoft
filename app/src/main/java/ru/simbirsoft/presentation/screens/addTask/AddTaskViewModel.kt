package ru.simbirsoft.presentation.screens.addTask

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.simbirsoft.di.locateLazy
import ru.simbirsoft.domain.model.Task
import ru.simbirsoft.domain.usecase.AddTaskUseCase
import ru.simbirsoft.presentation.base.BaseViewModel
import java.sql.Timestamp
import java.util.Calendar

class AddTaskViewModel : BaseViewModel() {


    private val addTaskUseCase: AddTaskUseCase by locateLazy()


    private val _calendarStart = MutableStateFlow<Calendar>(Calendar.getInstance())
    val calendarStart: StateFlow<Calendar> = _calendarStart

    private val _calendarFinish = MutableStateFlow<Calendar>(Calendar.getInstance().apply {
        val nowHour = get(Calendar.HOUR_OF_DAY)
        set(Calendar.HOUR_OF_DAY, nowHour + 1)
    })
    val calendarFinish: StateFlow<Calendar> = _calendarFinish

    fun addNewTask(title: String, description: String) {
        viewModelScope.launch {
            addTaskUseCase(
                Task(
                    name = title,
                    description = description,
                    dateFinish = Timestamp(calendarFinish.value.time.time),
                    dateStart = Timestamp(calendarStart.value.time.time),
                )
            )
        }
    }
}
