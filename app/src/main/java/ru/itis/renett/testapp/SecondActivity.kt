package ru.itis.renett.testapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.provider.AlarmClock
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import ru.itis.renett.testapp.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        val bundleFromIntentFromMainActivity: Bundle? = intent?.extras
        bundleFromIntentFromMainActivity?.getString("messageFromMainActivity")?.run {
            binding.btnAlarm.visibility = View.GONE
            binding.btnBack.visibility = View.VISIBLE
            showSnackbarMessage(this)
        }

        with (binding) {
            btnAlarm.setOnClickListener {
                createActualAlarm(intent)
                setResult(Activity.RESULT_OK)

                Thread.sleep(4000)   // SystemClock.sleep(1000)

                it.visibility = View.GONE
                btnBack.visibility = View.VISIBLE
            }
        }

        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun createActualAlarm(intent: Intent?) {
        val extras: Bundle? = intent?.extras
        var message = ""
        var hour = ""
        var minutes = ""

        if (extras != null) {
            message = extras.getString(AlarmClock.EXTRA_MESSAGE).toString()
            hour = extras.getString(AlarmClock.EXTRA_HOUR).toString()
            minutes = extras.getString(AlarmClock.EXTRA_MINUTES).toString()
        } else {
            message = "Test alarm to demonstrate how SecondActivity can be started by intent thanks to intent-filters"
            hour = "7"
            minutes = "30"
        }

        val actualIntent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, message)
            putExtra(AlarmClock.EXTRA_HOUR, hour)
            putExtra(AlarmClock.EXTRA_MINUTES, minutes)
            putExtra(AlarmClock.EXTRA_SKIP_UI, true)  // to bypass any confirmation UI
        }

        if (actualIntent.resolveActivity(packageManager) != null) {
            startActivity(actualIntent)
        }

        val messageForUser =
            "Alarm was created by another app, congrats! You can go back to MainActivity"
        showSnackbarMessage(messageForUser)
    }

    private fun showSnackbarMessage(messageForUser: String) {
        Snackbar.make(
            binding.root,
            messageForUser,
            Snackbar.LENGTH_LONG
        ).show()
    }
}
