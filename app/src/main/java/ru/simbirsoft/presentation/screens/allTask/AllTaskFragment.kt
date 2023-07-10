package ru.simbirsoft.presentation.screens.allTask

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
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
import ru.simbirsoft.presentation.screens.allTask.chooseFile.FileSelectionEntryPoint
import ru.simbirsoft.presentation.screens.allTask.chooseFile.SelectFileParams
import ru.simbirsoft.presentation.screens.allTask.chooseFile.StorageAccessFrameworkInteractor
import ru.simbirsoft.presentation.uiModel.TaskUi
import java.io.FileDescriptor

class AllTaskFragment : BaseFragment(R.layout.fragment_all_task), FileSelectionEntryPoint {

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
                        }

                        is AllTaskViewModel.UiState.Error -> {
                            visibleProgressBar(false)
                            if (uiState.exception is EmptyTaskException) {
                                onSelectFileClick(SelectFileParams())

                            } else {
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
    }


    private fun initCalendar() {
        viewBinding.calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            viewModel.selectedDayOfCalendar.value.set(year, month, dayOfMonth)
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

    override val fileSelectionOwner: Fragment = this
    private val fileSelectionInteractor: StorageAccessFrameworkInteractor =
        StorageAccessFrameworkInteractor(this)

    override fun onFileSelected(fileDescriptor: FileDescriptor?) {
        viewModel.getTaskForExternal(fileDescriptor)
    }

    private fun onSelectFileClick(selectFileParams: SelectFileParams) =
        fileSelectionInteractor.beginSelectingFile(selectFileParams)

}
