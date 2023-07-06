package ru.simbirsoft.presentation.screens.addTask

import by.kirich1409.viewbindingdelegate.viewBinding
import ru.simbirsoft.R
import ru.simbirsoft.databinding.FragmentAddTaskBinding
import ru.simbirsoft.presentation.base.BaseFragment

class AddTaskFragment : BaseFragment(R.layout.fragment_add_task) {

    private val viewBinding: FragmentAddTaskBinding by viewBinding(FragmentAddTaskBinding::bind)
}
