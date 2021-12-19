package ru.itis.renett.testapp.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import ru.itis.renett.testapp.R
import ru.itis.renett.testapp.database.AppDatabase
import ru.itis.renett.testapp.databinding.FragmentTaskBinding
import ru.itis.renett.testapp.entity.Task
import ru.itis.renett.testapp.listadapter.ItemConstants.EXTRA_TASK_ID
import java.util.*

class TaskFragment : Fragment(R.layout.fragment_task) {
    private var binding: FragmentTaskBinding? = null
    private lateinit var database: AppDatabase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTaskBinding.bind(view)

        database = AppDatabase.invoke(context) as AppDatabase

        if (arguments?.containsKey(EXTRA_TASK_ID) == true) {
            val task = arguments?.getInt(EXTRA_TASK_ID)?.let {
                database.taskDao().getTaskById(it)
            }

            task?.let { taskToDisplay ->
                binding?.also { binder ->
                    binder.etTaskTitle.setText(taskToDisplay.title)
                    binder.etTaskDescription.setText(taskToDisplay.description)
                    binder.etTaskDate.setText(taskToDisplay.description)
                }
            }
        }

        binding?.apply {
            tvLatitude.text = getUserLocationParameter("latitude").toString()
            tvLongitude.text = getUserLocationParameter("longitude").toString()

            etTaskDate.setOnClickListener {
                // create date picker
            }

            btnSave.setOnClickListener {
                getTaskFromInput()?.let {
                        task -> saveTaskToDatabase(task)
                }
                navigateToPreviousFragment()
            }
        }
    }

    private fun getUserLocationParameter(s: String): Double {
        return s.length.toDouble()
    }

    private fun getDateFromInput(): Date {
        return Date()
    }

    private fun getTaskFromInput(): Task? {
        var newTask: Task? = null
        binding?.apply {
            val title = etTaskTitle.text.toString()
            val description = etTaskDescription.text.toString()
            val date = getDateFromInput()
            val latitude = getUserLocationParameter("latitude")
            val longitude = getUserLocationParameter("longitude")

            newTask = Task(null, title, description, date, latitude, longitude)
        }

        return newTask
    }

    private fun saveTaskToDatabase(task: Task) {
        database.taskDao().save(task)
    }

    private fun navigateToPreviousFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, ListFragment())
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
