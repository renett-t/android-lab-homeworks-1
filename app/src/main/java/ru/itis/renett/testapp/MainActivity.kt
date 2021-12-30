package ru.itis.renett.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
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

    override fun onBackPressed() {
        super.onBackPressed()
        if (navController.currentDestination?.id == R.id.listFragment) {
            navController.popBackStack(R.id.listFragment, true)
            navController.navigate(R.id.listFragment)
        }
    }
}
