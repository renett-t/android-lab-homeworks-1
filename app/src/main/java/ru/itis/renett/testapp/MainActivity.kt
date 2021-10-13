package ru.itis.renett.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.itis.renett.testapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
    }
}
