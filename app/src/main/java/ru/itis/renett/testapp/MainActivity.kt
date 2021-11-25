package ru.itis.renett.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import ru.itis.renett.testapp.databinding.ActivityMainBinding
import ru.itis.renett.testapp.notification.AlarmService
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var timePicker: MaterialTimePicker
    private var alarmService: AlarmService? = null
    private var calendar: Calendar? = null
    private var timeIsSet: Boolean = false
    private var alarmIsCreated: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        alarmService = AlarmService(this)

        with(binding) {
            etHours.setOnClickListener {
                showTimePicker()
            }

            etMinutes.setOnClickListener {
                showTimePicker()
            }

            btnStart.setOnClickListener {
                if (timeIsSet) {
                    calendar?.let { calen ->
                        alarmService?.setAlarm(calen)
                        updateCurrentAlarmField(calen)
                        showMessage("Alarm set at ${calen.get(Calendar.HOUR_OF_DAY)} : ${calen.get(Calendar.MINUTE)}")
                    }
                    alarmIsCreated = true
                } else {
                    showMessage("Time wasn't set yet")
                }
            }

            btnStop.setOnClickListener {
                if (alarmIsCreated) {
                    clearCurrentAlarmField()
                    calendar?.let { calen ->
                        alarmService?.cancelAlarm(calen)
                        showMessage("Alarm at ${calen.get(Calendar.HOUR_OF_DAY)} : ${calen.get(Calendar.MINUTE)} is cancelled")
                    }
                    timeIsSet = false
                    alarmIsCreated = false
                } else {
                    showMessage("Alarm wasn't set yet")
                }
            }
        }
    }


    private fun showTimePicker() {
        timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(7)
            .setMinute(30)
            .setTitleText("Choose when you want to get an alarm")
            .build()

        with(timePicker) {
            show(supportFragmentManager, "ALARM")
            addOnPositiveButtonClickListener {
                calendar = Calendar.getInstance().also {
                    it[Calendar.HOUR_OF_DAY] = timePicker.hour
                    it[Calendar.MINUTE] = timePicker.minute
                    it[Calendar.SECOND] = 0
                    it[Calendar.MILLISECOND] = 0
                }
                binding.etHours.text = timePicker.hour.toString()
                binding.etMinutes.text = timePicker.minute.toString()
                timeIsSet = true
            }
        }
    }

    private fun clearCurrentAlarmField() {
        with(binding) {
            twActiveAlarm.text = "-"
            etHours.text = "-"
            etMinutes.text = "-"
        }
    }

    private fun updateCurrentAlarmField(time: Calendar) {
        with(binding) {
            twActiveAlarm.text = "${time[Calendar.HOUR_OF_DAY]} : ${time[Calendar.MINUTE]}"
        }
    }

    private fun showMessage(text: String) {
        binding.root.let {
            Snackbar.make(
                it,
                text,
                2000
            ).show()
        }
    }
}
