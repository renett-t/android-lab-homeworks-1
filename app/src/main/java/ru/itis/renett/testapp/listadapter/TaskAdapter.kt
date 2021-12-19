package ru.itis.renett.testapp.listadapter

import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.itis.renett.testapp.entity.Task
import ru.itis.renett.testapp.listadapter.ItemConstants.EXTRA_DATE
import ru.itis.renett.testapp.listadapter.ItemConstants.EXTRA_DESCRIPTION
import ru.itis.renett.testapp.listadapter.ItemConstants.EXTRA_TITLE
import ru.itis.renett.testapp.listadapter.ItemConstants.getDateFormatted

class TaskListAdapter (
    private val onItemChosenAction: (Int) -> Unit,
    private val onItemDeleteAction: (Int) -> Unit
) : ListAdapter<Task, TaskHolder>(TaskDiffUtilItemCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder =
        TaskHolder.create(parent, onItemChosenAction, onItemDeleteAction)

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            payloads.last().takeIf {
                    it is Bundle
                }?.let {
                    holder.updateFields(it as Bundle)
                } ?: run {
                    super.onBindViewHolder(holder, position, payloads)
                }
        }
    }

    override fun submitList(list: MutableList<Task>?) {
        super.submitList(if (list == null) null else ArrayList(list))
    }
}

class TaskDiffUtilItemCallback : DiffUtil.ItemCallback<Task>() {

    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean = oldItem == newItem

    override fun getChangePayload(oldItem: Task, newItem: Task): Any? {
        val bundle = Bundle()

        if (oldItem.title != newItem.title)
            bundle.putString(EXTRA_TITLE, newItem.title)
        if (oldItem.description != newItem.description)
            bundle.putString(EXTRA_DESCRIPTION, newItem.description)
        if (oldItem.date != newItem.date)
            bundle.putString(EXTRA_DATE, getDateFormatted(newItem.date))

        return bundle;
    }
}
