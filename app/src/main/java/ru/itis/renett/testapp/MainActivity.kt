package ru.itis.renett.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import com.google.android.material.snackbar.Snackbar
import ru.itis.renett.testapp.database.TaskDatabase
import ru.itis.renett.testapp.databinding.ActivityMainBinding
import ru.itis.renett.testapp.extentions.findController

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    lateinit var taskDB: TaskDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        taskDB = TaskDatabase.getInstance(applicationContext)

        navController = findController(R.id.fragment_container)
    }
}
