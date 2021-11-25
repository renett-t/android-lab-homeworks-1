package ru.itis.renett.testapp.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import ru.itis.renett.testapp.R

class NotificationChannelService(
    private val context: Context
) {
    private val manager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private val vibrPattern = longArrayOf(100L, 200L, 0, 400L)

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                context.getString(R.string.channel_id),
                context.getString(R.string.notification_channel_title),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                setSound(getSomeSound(), getSomeAudioAttributes())
                vibrationPattern = vibrPattern
                description = context.getString(R.string.notification_channel_desc)
            }.also {
                manager.createNotificationChannel(it)
            }
        }
    }

    private fun getSomeSound(): Uri? {
        return Uri.parse("android.resource://" + context.packageName + "/" + R.raw.alarm_skz)
    }

    private fun getSomeAudioAttributes(): AudioAttributes {
        return AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .build()
    }
}
