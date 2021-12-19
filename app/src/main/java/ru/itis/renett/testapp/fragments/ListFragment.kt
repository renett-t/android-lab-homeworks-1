package ru.itis.renett.testapp.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ru.itis.renett.testapp.R
import ru.itis.renett.testapp.database.AppDatabase
import ru.itis.renett.testapp.databinding.FragmentListBinding
import ru.itis.renett.testapp.entity.Task
import ru.itis.renett.testapp.listadapter.ItemConstants.EXTRA_TASK_ID
import ru.itis.renett.testapp.listadapter.TaskListAdapter

class ListFragment : Fragment(R.layout.fragment_list) {

    private var binding: FragmentListBinding? = null
    private lateinit var database: AppDatabase
    private var taskListAdapter: TaskListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListBinding.bind(view)

        database = AppDatabase.invoke(context) as AppDatabase

        taskListAdapter = TaskListAdapter({navigateToFragment(it)}, {deleteItemById(it)})

        with(binding) {
            this?.fabtnAdd?.setOnClickListener {
                navigateToFragment(null)
            }

            this?.rvTasks?.run {
                adapter = taskListAdapter
                addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
            }
        }

        val list = database.taskDao().findAllTasks()

        if (list.isEmpty()) {
            binding?.tvStart?.visibility = View.VISIBLE
            binding?.rvTasks?.visibility = View.GONE
        } else {
            updateTaskList(list)
        }
    }

    private fun updateTaskList(newList: List<Task>) {
        if (newList.isEmpty()) {
            binding?.tvStart?.visibility = View.VISIBLE
            binding?.rvTasks?.visibility = View.GONE
        } else {
            binding?.tvStart?.visibility = View.GONE
            binding?.rvTasks?.visibility = View.VISIBLE
        }
        taskListAdapter?.submitList(ArrayList(newList))
    }

    private fun navigateToFragment(id: Int?) {
        val newFragment = TaskFragment()
        id?.let { extraId ->
            newFragment.arguments = Bundle().apply {
                putInt(EXTRA_TASK_ID, extraId)
            }
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, newFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun deleteItemById(id: Int) {
        database.taskDao().deleteTaskById(id)
        showMessage("Элемент $id был удалён из списка")
    }

    private fun showMessage(message: String) {
        Snackbar.make(
            requireActivity().findViewById(R.id.fragment_container),
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
