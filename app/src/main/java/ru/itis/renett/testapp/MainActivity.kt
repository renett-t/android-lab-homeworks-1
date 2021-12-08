package ru.itis.renett.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import ru.itis.renett.testapp.databinding.ActivityMainBinding
import ru.itis.renett.testapp.fragments.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        var newFragment: Fragment? = null
        if (intent.getIntExtra(EXTRA_PROGRESS, -1) != -1){
            newFragment = OneTrackFragment()
            val bundle = Bundle().apply {
                putInt(EXTRA_ID, intent.getIntExtra(EXTRA_ID, -1))
            }
            newFragment.arguments = bundle
        } else {
            newFragment = TracksFragment()
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, newFragment)
            .commit()
    }
}
