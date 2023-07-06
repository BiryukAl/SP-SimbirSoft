package ru.simbirsoft.presentation.screens.allTask

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.launch
import ru.simbirsoft.R
import ru.simbirsoft.databinding.FragmentAllTaskBinding
import ru.simbirsoft.presentation.adapters.TaskAdapter
import ru.simbirsoft.presentation.base.BaseFragment
import ru.simbirsoft.presentation.uiModel.TaskUi
import java.util.Calendar
import java.util.Date

class AllTaskFragment : BaseFragment(R.layout.fragment_all_task) {
    private val viewBinding: FragmentAllTaskBinding by viewBinding(FragmentAllTaskBinding::bind)
    private val viewModel: AllTaskViewModel by viewModels<AllTaskViewModel>()
    private val adapter: TaskAdapter? get() = viewBinding.taskList.adapter as? TaskAdapter

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.taskList.adapter = TaskAdapter()

        viewBinding.calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            Toast.makeText(
                context,
                "year: $year, month: $month, dayOfMonth $dayOfMonth",
                Toast.LENGTH_SHORT
            ).show()

            val nowDay: Date
            Calendar.getInstance().apply {
                set(year, month, dayOfMonth, 0, 0)
                nowDay = time
            }
            Log.d("MY_TAG", nowDay.toString())

            viewModel.updateTaskByDate(nowDay)
        }



        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is AllTaskViewModel.UiState.Success -> renderTask(uiState.tasks)
                        is AllTaskViewModel.UiState.Loading -> {}
                        is AllTaskViewModel.UiState.Error -> {}
                    }
                }
            }
        }

    }

    private fun renderTask(tasks: List<TaskUi>) {
        adapter?.submitList(tasks)
    }

    companion object {
        const val ALL_TASK_FRAGMENT_TAG = "ALL_TASK_FRAGMENT_TAG"

        fun getInstance() = AllTaskFragment()
    }
}
