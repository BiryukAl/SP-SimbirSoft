package ru.simbirsoft.presentation.screens.allTask

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.simbirsoft.di.locateLazy
import ru.simbirsoft.domain.usecase.AddFromExternalUseCase
import ru.simbirsoft.domain.usecase.GetAllTaskUseCase
import ru.simbirsoft.domain.usecase.GetTaskOnDayUseCase
import ru.simbirsoft.presentation.MapperUi
import ru.simbirsoft.presentation.base.BaseViewModel
import ru.simbirsoft.presentation.uiModel.TaskUi
import java.io.FileDescriptor
import java.sql.Timestamp
import java.util.Calendar
import java.util.Date


class AllTaskViewModel : BaseViewModel() {

    sealed class UiState {
        object Loading : UiState()
        data class Success(
            val tasks: List<TaskUi>
        ) : UiState()

        data class Error(val exception: Throwable) : UiState()

    }

    private val getTaskOnDayUseCase: GetTaskOnDayUseCase by locateLazy()
    private val getAllTaskUseCase: GetAllTaskUseCase by locateLazy()
    private val addFromExternalUseCase: AddFromExternalUseCase by locateLazy()
    private val mapperUi: MapperUi by locateLazy()


    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    private val _selectedDayOfCalendar = MutableStateFlow<Calendar>(Calendar.getInstance())
    val selectedDayOfCalendar: StateFlow<Calendar> = _selectedDayOfCalendar



    init {
        isTaskForDataEmpty()
        updateTask()

    }

    fun updateTask() {
        _uiState.value = UiState.Loading
        val nowDay = mapDateToTimestamp(selectedDayOfCalendar.value.time)
        viewModelScope.launch {
            getTaskOnDayUseCase(nowDay).collect {
                val taskUi: MutableList<TaskUi> = mutableListOf()
                val allHour: MutableSet<Int> = mutableSetOf()
                it.forEach { task ->
                    val cal = Calendar.getInstance()
                    cal.time = task.dateStart
                    val hourTask = cal.get(Calendar.HOUR_OF_DAY)
                    if (hourTask !in allHour) {
                        taskUi.add(TaskUi.SeparatorHour(hourTask))
                        allHour.add(hourTask)
                    }
                    taskUi.add(mapperUi.toTaskUi(task))
                }
                _uiState.value = UiState.Success(taskUi)
            }
        }
    }

    private fun mapDateToTimestamp(date: Date): Timestamp {
        return Timestamp((date.time / GetTaskOnDayUseCase.DAY) * GetTaskOnDayUseCase.DAY)
    }

    private fun isTaskForDataEmpty() {
        viewModelScope.launch {
            getAllTaskUseCase().collect {
                if (it.isEmpty()) {
                    _uiState.value = UiState.Error(EmptyTaskException())
                }
            }
        }
    }

    fun getTaskForExternal(fileDescriptor: FileDescriptor?) {
        viewModelScope.launch {
            addFromExternalUseCase(fileDescriptor)
        }
        _uiState.value = UiState.Loading
    }

}
