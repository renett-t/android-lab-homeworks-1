package ru.itis.renett.testapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.itis.renett.testapp.databinding.ActivityWokenUpBinding

class WokenUpActivity: AppCompatActivity() {

    private lateinit var binding: ActivityWokenUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWokenUpBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
    }
}
