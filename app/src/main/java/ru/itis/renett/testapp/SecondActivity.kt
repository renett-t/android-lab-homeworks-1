package ru.itis.renett.testapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.itis.renett.testapp.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

    }
}
