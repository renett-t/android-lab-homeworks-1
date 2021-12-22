package ru.itis.renett.testapp.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ru.itis.renett.testapp.R
import ru.itis.renett.testapp.database.TaskDatabase
import ru.itis.renett.testapp.databinding.FragmentListBinding
import ru.itis.renett.testapp.entity.Task
import ru.itis.renett.testapp.listadapter.ItemConstants.EXTRA_TASK_ID
import ru.itis.renett.testapp.listadapter.TaskListAdapter

class ListFragment : Fragment(R.layout.fragment_list) {

    private lateinit var binding: FragmentListBinding
    private lateinit var database: TaskDatabase
    private var taskListAdapter: TaskListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListBinding.bind(view)

        database = TaskDatabase.getInstance(this.requireContext())

        taskListAdapter = TaskListAdapter({navigateToFragment(it)}, {deleteItemById(it)})

        with(binding) {
            toolbar.setOnMenuItemClickListener {
                onOptionsItemSelected(it)
            }

            fabtnAdd.setOnClickListener {
                navigateToFragment(null)
            }

            rvTasks.run {
                adapter = taskListAdapter
                addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
            }
        }

        val list = database.taskDao().findAllTasks()


        if (list.isEmpty()) {
            binding.tvStart.visibility = View.VISIBLE
            binding.rvTasks.visibility = View.GONE
        } else {
            updateTaskList(list)
        }

    }

    private fun updateTaskList(newList: List<Task>) {
        with(binding) {
            if (newList.isEmpty()) {
                tvStart.visibility = View.VISIBLE
                rvTasks.visibility = View.GONE
            } else {
                tvStart.visibility = View.GONE
                rvTasks.visibility = View.VISIBLE
            }
        }
        taskListAdapter?.submitList(ArrayList(newList))
    }

    private fun navigateToFragment(id: Int?) {
        var bundle: Bundle? = null
        id?.let {
            bundle = Bundle().apply {
                putInt(EXTRA_TASK_ID, id)
            }
        }
        val options = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .build()

        findNavController().navigate(
            R.id.action_listFragment_to_taskFragment,
            bundle,
            options
        )
    }

    private fun deleteItemById(id: Int) {
        database.taskDao().deleteTaskById(id)
        updateTaskList(database.taskDao().findAllTasks())
        showMessage("Элемент $id был удалён из списка")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_item_delete) {
            if (database.taskDao().findAllTasks().isEmpty()) {
                showMessage(getString(R.string.no_task_saved))
            } else {
                AlertDialog.Builder(this.requireContext())
                    .setMessage(R.string.notif_delete_all_tasks)
                    .setPositiveButton(R.string.yes) {
                            dialog, _ ->
                        database.taskDao().deleteAll()
                        updateTaskList(database.taskDao().findAllTasks())
                        dialog.dismiss()
                    }
                    .setNegativeButton(R.string.no) {
                            dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }

        return true
    }

    private fun showMessage(message: String) {
        Snackbar.make(
            requireActivity().findViewById(R.id.fragment_container),
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }
}
