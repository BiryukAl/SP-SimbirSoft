package ru.simbirsoft.presentation.screens.allTask

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.simbirsoft.di.locateLazy
import ru.simbirsoft.domain.usecase.GetTaskOnDayUseCase
import ru.simbirsoft.presentation.MapperUi
import ru.simbirsoft.presentation.base.BaseViewModel
import ru.simbirsoft.presentation.uiModel.TaskUi
import java.sql.Timestamp
import java.util.Calendar
import java.util.Date


class AllTaskViewModel : BaseViewModel() {

    sealed class UiState {
        object Loading : UiState()
        data class Success(
            val day: Timestamp,
            val tasks: List<TaskUi>
        ) : UiState()

        data class Error(val exception: Throwable) : UiState()

    }

    private val getTaskOnDayUseCase: GetTaskOnDayUseCase by locateLazy()
    private val mapperUi: MapperUi by locateLazy()


    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    init {
        updateTaskByDate(Calendar.getInstance().time)
    }

    fun updateTaskByDate(date: Date) {
        _uiState.value = UiState.Loading

        val nowDay = mapDateToTimestamp(date)
        viewModelScope.launch {
            getTaskOnDayUseCase(nowDay).collect {

                _uiState.value = UiState.Success(nowDay, it.map(mapperUi::toTaskUi))
            }
        }
    }

    private fun mapDateToTimestamp(date: Date): Timestamp {
        return Timestamp((date.time / GetTaskOnDayUseCase.DAY) * GetTaskOnDayUseCase.DAY)
    }

}
