package ru.itis.renett.testapp.fragments

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
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
import android.location.Location
import android.util.Log
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import ru.itis.renett.testapp.listadapter.ItemConstants.getDateFormatted

class TaskFragment : Fragment(R.layout.fragment_task) {
    private var binding: FragmentTaskBinding? = null
    private lateinit var database: TaskDatabase
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var currentTaskId: Int? = null
    private val PERMISSION_REQ_CODE: Int = 222
    private var userLatitude: Double? = null
    private var userLongitude: Double? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTaskBinding.bind(view)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.requireActivity())

        database = TaskDatabase.getInstance(this.requireContext())

        initializeUserLocationParameters()
        Log.e("LOCATION", "LOCATION INITIALIZED $userLatitude, $userLongitude")

        if (arguments?.containsKey(EXTRA_TASK_ID) == true) {
            val task = arguments?.getInt(EXTRA_TASK_ID)?.let {
                currentTaskId = it
                database.taskDao().getTaskById(it)
            }

            task?.let { taskToDisplay ->
                binding?.also { binder ->
                    binder.etTaskTitle.setText(taskToDisplay.title)
                    binder.etTaskDescription.setText(taskToDisplay.description)
                    binder.etTaskDate.setText(getDateFormatted(taskToDisplay.date ?: Date()))
                }
            }

        }

        binding?.apply {
            etTaskDate.setOnClickListener {
                // create date picker
                // get date
                // change etTaskDate
            }

            btnSave.setOnClickListener {
                getTaskFromInput()?.let {
                        task -> saveTaskToDatabase(task)
                }
                navigateToPreviousFragment()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun initializeUserLocationParameters() {
        activity?.apply {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.e("LOCATION", "Location permissions not granted. Requesting.")
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                    PERMISSION_REQ_CODE)
            } else {
                Log.e("LOCATION", "Location permissions granted. Getting the location.")
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        userLatitude = location?.latitude
                        userLongitude = location?.longitude
                    }

                binding?.apply {
                    tvLatitude.text = if (userLatitude != null) userLatitude.toString() else "No data"
                    tvLongitude.text = if (userLongitude != null) userLongitude.toString() else "No data"
                }

                Log.e("LOCATION", "LOCATION INITIALIZED $userLatitude, $userLongitude")
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        Log.e("LOCATION", "Getting the result of check")

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
        return Date()
    }

    private fun getTaskFromInput(): Task? {
        var newTask: Task? = null
        binding?.apply {
            val title = etTaskTitle.text.toString()
            val description = etTaskDescription.text.toString()
            val date = getDateFromInput()
            initializeUserLocationParameters()
            val latitude = userLatitude
            val longitude = userLongitude

            val id: Int = currentTaskId ?: 0
            newTask = Task(id, title, description, date, latitude, longitude)
        }

        return newTask
    }

    private fun saveTaskToDatabase(task: Task) {
        database.taskDao().save(task)
    }

    private fun navigateToPreviousFragment() {
        val options = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .build()

//        findNavController().popBackStack()

        findNavController().navigate(
            R.id.action_taskFragment_to_listFragment,
            null,
            options
        )
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
