package ru.itis.renett.testapp.notification

import android.app.AlarmManager
import android.app.AlarmManager.RTC_WAKEUP
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import ru.itis.renett.testapp.R
import java.util.*

class AlarmService(
    private val context: Context
) {
    private val alarmManager: AlarmManager
    private var pendingIntent: PendingIntent? = null
    private val REQ_CODE = 0

    init {
        NotificationChannelService(context).also {
            it.createNotificationChannel()
        }
        alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
    }

    fun cancelAlarm(calendar: Calendar) {
        alarmManager.cancel(pendingIntent)
        pendingIntent = null
    }

    fun setAlarm(calendar: Calendar) {
        val intent = Intent(context.applicationContext, AlarmReceiver::class.java).also {
            it.action = context.getString(R.string.action_alarm)
        }

        pendingIntent = PendingIntent.getBroadcast(
            context.applicationContext,
            REQ_CODE,
            intent,
            FLAG_UPDATE_CURRENT
        )

        alarmManager.setExact(RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }
}
