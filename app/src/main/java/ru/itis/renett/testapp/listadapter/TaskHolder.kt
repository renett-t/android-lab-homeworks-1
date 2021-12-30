package ru.itis.renett.testapp.listadapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.itis.renett.testapp.databinding.ItemTaskBinding
import ru.itis.renett.testapp.entity.Task
import ru.itis.renett.testapp.listadapter.ItemConstants.EXTRA_DATE
import ru.itis.renett.testapp.listadapter.ItemConstants.EXTRA_DESCRIPTION
import ru.itis.renett.testapp.listadapter.ItemConstants.EXTRA_TITLE
import ru.itis.renett.testapp.listadapter.ItemConstants.getDateFormatted

class TaskHolder (
    private val binding: ItemTaskBinding,
    private val onItemChosenAction: (Int) -> Unit,
    private val onItemDeleteAction: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var taskToDisplay: Task? = null

    init {
        itemView.setOnClickListener {
            taskToDisplay?.run {
                this.id?.let { it1 -> onItemChosenAction(it1) }
            }
        }
    }

    fun bind(task: Task): Unit {
        this.taskToDisplay = task
        with(binding) {
            tvTitle.text = task.title
            tvDescription.text = task.description
            tvDueDate.text = getDateFormatted(task.date)

            ibtnDelete.setOnClickListener {
                taskToDisplay?.run {
                    this.id?.let { it1 -> onItemDeleteAction(it1) }
                }
            }
        }
    }

    fun updateFields(bundle: Bundle?) {
        with(binding) {
            if (bundle?.containsKey(EXTRA_TITLE) == true) {
                bundle.getString(EXTRA_TITLE).also {
                    tvTitle.text = it
                }
            }

            if (bundle?.containsKey(EXTRA_DESCRIPTION) == true) {
                bundle.getString(EXTRA_DESCRIPTION).also {
                    tvDescription.text = it
                }
            }

            if (bundle?.containsKey(EXTRA_DATE) == true) {
                bundle.getString(EXTRA_DATE).also {
                    tvDueDate.text = it
                }
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup,onItemChosenAction: (Int) -> Unit, onItemDeleteAction: (Int) -> Unit) =
            TaskHolder (ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                        onItemChosenAction, onItemDeleteAction)
    }
}
