package ru.itis.renett.testapp.fragments

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import ru.itis.renett.testapp.R
import ru.itis.renett.testapp.database.TaskDatabase
import ru.itis.renett.testapp.databinding.FragmentTaskBinding
import ru.itis.renett.testapp.entity.Task
import ru.itis.renett.testapp.listadapter.ItemConstants.EXTRA_TASK_ID
import java.util.*
import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import ru.itis.renett.testapp.listadapter.ItemConstants.getDateFormatted

class TaskFragment : Fragment(R.layout.fragment_task) {
    private var binding: FragmentTaskBinding? = null
    private lateinit var database: TaskDatabase
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private var currentTaskId: Int = -1
    private val PERMISSION_REQ_CODE: Int = 222
    private var userLatitude: Double? = null
    private var userLongitude: Double? = null
    private var chosenDate: Date? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTaskBinding.bind(view)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.requireActivity())

        database = TaskDatabase.getInstance(this.requireContext())

        initializeUserLocationParameters()

        if (arguments?.containsKey(EXTRA_TASK_ID) == true) {
            scope.launch {
                arguments?.getInt(EXTRA_TASK_ID)?.let {
                    initTaskView(it)
                }
            }
        }

        binding?.run {
            btnSetDate.setOnClickListener {
                createDatePickerDialog()
            }

            btnSave.setOnClickListener {
                getTaskFromInput()?.let { task ->
                    scope.launch {
                        saveTaskToDatabase(task)
                        navigateToPreviousFragment()
                    }
                }
            }
        }
    }

    private suspend fun initTaskView(id: Int) {
        currentTaskId = id
        var task : Task? = null
        withContext(Dispatchers.IO) {
            task = database.taskDao().getTaskById(currentTaskId)
        }
        task?.let { taskToDisplay ->
            binding?.run {
                etTaskTitle.setText(taskToDisplay.title)
                etTaskDescription.setText(taskToDisplay.description)
                updateDateField(taskToDisplay.date ?: Date())
                updateLocationFields(taskToDisplay.latitude, taskToDisplay.longitude)
            }
        }
    }

    private fun updateLocationFields(latitude: Double?, longitude: Double?) {
        binding?.apply {
            tvLatitude.text = latitude?.toString() ?: "No data"
            tvLongitude.text = longitude?.toString() ?: "No data"
        }
    }

    private fun updateDateField(date: Date) {
        binding?.tvTaskDate?.text = (getDateFormatted(date))
    }

    @SuppressLint("MissingPermission")
    private fun initializeUserLocationParameters() {
        if (checkPermissionsGranted()) {
            fusedLocationClient.lastLocation
                .addOnCompleteListener {
                    val location = it.result
                    userLatitude = location?.latitude
                    userLongitude = location?.longitude
                    updateLocationFields(userLatitude, userLongitude)
                }
        } else {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                PERMISSION_REQ_CODE)
        }
    }

    private fun checkPermissionsGranted(): Boolean {
        activity?.run {
            return (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED)
        }

        return false
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            PERMISSION_REQ_CODE -> {
                if (grantResults.isNotEmpty() &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initializeUserLocationParameters()
                } else {
                    showMessage("Sorry, location service isn't available because there's no permission granted")
                }
            }
            else -> {
                // ignore
            }
        }
    }

    private fun getDateFromInput(): Date {
        return chosenDate ?: Date()
    }

    private fun getTaskFromInput(): Task? {
        var newTask: Task? = null
        binding?.apply {
            if (etTaskTitle.text.toString().isNotEmpty()) {
                val title = etTaskTitle.text.toString()
                val description = etTaskDescription.text.toString()
                val date = getDateFromInput()
                initializeUserLocationParameters()
                val latitude = userLatitude
                val longitude = userLongitude

                var id = 0
                if (currentTaskId > -1)
                    id = currentTaskId

                newTask = Task(id, title, description, date, latitude, longitude)
            } else {
                showMessage("Title cannot be empty")
                return null
            }
        }

        return newTask
    }

    private suspend fun saveTaskToDatabase(task: Task) {
        withContext(Dispatchers.IO) {
            database.taskDao().save(task)
        }
    }

    private fun navigateToPreviousFragment() {
        findNavController().popBackStack()
    }

    private fun createDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(this.requireContext(),
            DatePickerDialog.OnDateSetListener() {_ , year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear - 1)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                calendar.add(Calendar.MONTH, 1)
                chosenDate = calendar.time
                updateDateField(calendar.time)
        }, year, month, day)

        datePicker.show()
    }

    private fun showMessage(message: String) {
        Snackbar.make(
            requireActivity().findViewById(R.id.fragment_container),
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        scope.cancel()
    }
}
