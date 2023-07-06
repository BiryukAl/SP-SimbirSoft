package ru.simbirsoft.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.simbirsoft.R
import ru.simbirsoft.databinding.CardHourSeparatorBinding
import ru.simbirsoft.databinding.CardTaskBinding
import ru.simbirsoft.presentation.uiModel.TaskUi

class TaskAdapter() :
    ListAdapter<TaskUi, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<TaskUi>() {
        override fun areItemsTheSame(
            oldItem: TaskUi,
            newItem: TaskUi
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: TaskUi,
            newItem: TaskUi
        ): Boolean = oldItem == newItem

    }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            RecyclerView.ViewHolder = when (viewType) {
        R.layout.card_task -> TaskViewHolder(
            viewBinding = CardTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

        R.layout.card_hour_separator -> HourSeparatorViewHolder(
            viewBinding = CardHourSeparatorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

        else -> throw IllegalArgumentException("No ViewHolder for such item")
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = currentList[position]
        when (holder) {
            is TaskViewHolder -> holder.onBind(item as TaskUi.Task)
            is HourSeparatorViewHolder -> holder.onBind(item as TaskUi.SeparatorHour)
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (currentList[position]) {
            is TaskUi.Task -> R.layout.card_task
            is TaskUi.SeparatorHour -> R.layout.card_hour_separator
        }

    inner class TaskViewHolder(private val viewBinding: CardTaskBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun onBind(item: TaskUi.Task) {
            with(viewBinding) {
                titleTask.text = item.name
                descriptionTask.text = item.description
                timeStartTask.text = item.dateStart
                timeEndTask.text = item.dateFinish
            }
        }
    }

    inner class HourSeparatorViewHolder(
        private val viewBinding: CardHourSeparatorBinding
    ) : RecyclerView.ViewHolder(viewBinding.root) {

        fun onBind(item: TaskUi.SeparatorHour) {
            with(viewBinding) {
                separatorHour.text = item.hour.toString()
            }
        }
    }
}
