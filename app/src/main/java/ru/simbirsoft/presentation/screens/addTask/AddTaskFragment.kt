package ru.simbirsoft.presentation.screens.addTask

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.simbirsoft.R
import ru.simbirsoft.databinding.FragmentAddTaskBinding
import ru.simbirsoft.presentation.base.BaseFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddTaskFragment : BaseFragment(R.layout.fragment_add_task) {

    private val viewBinding: FragmentAddTaskBinding by viewBinding(FragmentAddTaskBinding::bind)
    private val viewModel: AddTaskViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSelectDateNewTask()
        initSelectTimesNewTask()
        updateDateViewText()
        updateTimeViewsText()
        initBtnAddTask()
    }

    private fun initBtnAddTask() {
        viewBinding.btnAddTask.setOnClickListener {
            if (checkTitleIsEmpty()){
                showMessageIncorrectData()
                return@setOnClickListener
            }
            viewModel.addNewTask(
                viewBinding.edTitleTask.text.toString(),
                viewBinding.edDescriptionTask.text.toString()
            )
            parentFragmentManager.popBackStack()
        }
    }

    private fun checkTitleIsEmpty(): Boolean {
        return viewBinding.edTitleTask.text.toString().isEmpty()
    }

    private fun initSelectTimesNewTask() {
        context?.let { context ->
            viewBinding.timeStartNewTask.setOnClickListener {
                TimePickerDialog(
                    context,
                    { _, hour, minute ->
                        setNewTaskTimeStart(hour, minute)
                        updateTimeViewsText()
                    },
                    viewModel.calendarStart.value.get(Calendar.HOUR_OF_DAY),
                    viewModel.calendarStart.value.get(Calendar.MINUTE),
                    true
                ).show()
            }

            viewBinding.timeFinishNewTask.setOnClickListener {
                TimePickerDialog(
                    context,
                    { _, hour, minute ->
                        setNewTaskTimeFinish(hour, minute)
                        updateTimeViewsText()
                    },
                    viewModel.calendarFinish.value.get(Calendar.HOUR_OF_DAY),
                    viewModel.calendarFinish.value.get(Calendar.MINUTE),
                    true
                ).show()
            }
        }
    }

    private fun initSelectDateNewTask() {
        viewBinding.dateNewTask.setOnClickListener {
            context?.let { context ->
                DatePickerDialog(
                    context,
                    { _, year, monthOfYear, dayOfMonth ->
                        setNewTaskDate(year, monthOfYear, dayOfMonth)
                        updateDateViewText()
                    },
                    viewModel.calendarStart.value.get(Calendar.YEAR),
                    viewModel.calendarStart.value.get(Calendar.MONTH),
                    viewModel.calendarStart.value.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

        }
    }


    private fun setNewTaskDate(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        with(viewModel.calendarStart.value) {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, monthOfYear)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }

        with(viewModel.calendarFinish.value) {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, monthOfYear)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }
    }

    private fun setNewTaskTimeStart(hour: Int, minute: Int) {
        val finishTimeHour = viewModel.calendarFinish.value.get(Calendar.HOUR_OF_DAY)
        val finishTimeMinute = viewModel.calendarFinish.value.get(Calendar.MINUTE)

        if (hour > finishTimeHour) {
            viewModel.calendarStart.value.set(Calendar.HOUR_OF_DAY, hour)
            viewModel.calendarStart.value.set(Calendar.MINUTE, minute)

            try {
                viewModel.calendarFinish.value.set(Calendar.HOUR_OF_DAY, hour + 1)
            } catch (ex: IndexOutOfBoundsException) {
                showMessageIncorrectData()
                return
            }
            viewModel.calendarFinish.value.set(Calendar.MINUTE, minute)
            return
        }
        if (hour == finishTimeHour) {
            viewModel.calendarStart.value.set(Calendar.HOUR_OF_DAY, hour)

            if (minute >= finishTimeMinute) {
                viewModel.calendarStart.value.set(Calendar.MINUTE, minute)

                try {
                    viewModel.calendarFinish.value.set(Calendar.MINUTE, minute + 1)
                } catch (ex: IndexOutOfBoundsException) {
                    showMessageIncorrectData()
                    return
                }
            }
            return
        }
        viewModel.calendarStart.value.set(Calendar.HOUR_OF_DAY, hour)
        viewModel.calendarStart.value.set(Calendar.MINUTE, minute)
    }

    private fun setNewTaskTimeFinish(hour: Int, minute: Int) {
        val startTimeHour = viewModel.calendarStart.value.get(Calendar.HOUR_OF_DAY)
        val startTimeMinute = viewModel.calendarStart.value.get(Calendar.MINUTE)

        if (startTimeHour > hour) {
            showMessageIncorrectData()
            return
        }
        if (startTimeHour == hour) {
            if (startTimeMinute >= minute) {
                showMessageIncorrectData()
            }
        }

        viewModel.calendarFinish.value.set(Calendar.HOUR_OF_DAY, hour)
        viewModel.calendarFinish.value.set(Calendar.MINUTE, minute)
    }


    private fun updateDateViewText() {
        val startData = viewModel.calendarStart.value.time

        viewBinding.dateNewTask.text =
            SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(startData)
    }

    private fun updateTimeViewsText() {
        val startTime = viewModel.calendarStart.value.time
        val finishTime = viewModel.calendarFinish.value.time

        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        viewBinding.timeStartNewTask.text = formatter.format(startTime)
        viewBinding.timeFinishNewTask.text = formatter.format(finishTime)
    }

    private fun showMessageIncorrectData() {
        Toast.makeText(context, "Incorrect Data", Toast.LENGTH_SHORT).show()
    }


    companion object {
        const val ADD_TASK_FRAGMENT_TAG = "ADD_TASK_FRAGMENT_TAG"

        fun getInstance() = AddTaskFragment()
    }
}
