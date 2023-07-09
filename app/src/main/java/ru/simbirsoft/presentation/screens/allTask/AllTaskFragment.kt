package ru.simbirsoft.presentation.screens.allTask

import android.annotation.SuppressLint
import android.os.Bundle
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
import ru.simbirsoft.presentation.screens.addTask.AddTaskFragment
import ru.simbirsoft.presentation.uiModel.TaskUi

class AllTaskFragment : BaseFragment(R.layout.fragment_all_task) {

    private val viewBinding: FragmentAllTaskBinding by viewBinding(FragmentAllTaskBinding::bind)
    private val viewModel: AllTaskViewModel by viewModels<AllTaskViewModel>()
    private val adapter: TaskAdapter? get() = viewBinding.taskList.adapter as? TaskAdapter

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.taskList.adapter = TaskAdapter()

        initCalendar()
        initButtonAddTask()
        renderState()
    }

    private fun renderState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is AllTaskViewModel.UiState.Success -> {
                            visibleProgressBar(false)
                            renderTask(uiState.tasks)
                        }

                        is AllTaskViewModel.UiState.Loading -> {
                            visibleProgressBar(true)
                            // TODO: Visible progress bar
                        }

                        is AllTaskViewModel.UiState.Error -> {
                            visibleProgressBar(false)
                            Toast.makeText(
                                context,
                                "Error update task: ${uiState.exception}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun initCalendar() {
        viewBinding.calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            viewModel.calendar.value.set(year, month, dayOfMonth, 0, 0)
            viewModel.updateTask()
        }
    }

    private fun initButtonAddTask() {
        viewBinding.btnAddTask.setOnClickListener {
            replaceFragment(
                AddTaskFragment.getInstance(),
                AddTaskFragment.ADD_TASK_FRAGMENT_TAG
            )
        }
    }

    private fun renderTask(tasks: List<TaskUi>) {
        adapter?.submitList(tasks)
    }

    private fun visibleProgressBar(visible: Boolean) {
        viewBinding.progressBar.visibility = if (visible) View.VISIBLE else View.GONE
        viewBinding.taskList.visibility = if (!visible) View.VISIBLE else View.GONE
    }

    companion object {
        const val ALL_TASK_FRAGMENT_TAG = "ALL_TASK_FRAGMENT_TAG"

        fun getInstance() = AllTaskFragment()
    }
}
