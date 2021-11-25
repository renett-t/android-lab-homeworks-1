package ru.itis.renett.testapp.notification

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.core.app.NotificationCompat
import ru.itis.renett.testapp.R
import ru.itis.renett.testapp.WokenUpActivity

class AlarmReceiver : BroadcastReceiver() {
    private val req_code = 0
    private val notif_id = 1

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            if (it.action == context?.getString(R.string.action_alarm)) {
                val pendingIntent: PendingIntent? = getIntentToStartActivity(context)

                (context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                    .notify(notif_id, createNotification(context, pendingIntent))
            }
        }
    }

    private fun getIntentToStartActivity(context: Context?): PendingIntent? {
        val intent = Intent(context, WokenUpActivity::class.java)

        return PendingIntent.getActivity(
                context,
                req_code,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun createNotification(context: Context?, intent: PendingIntent?): Notification? {
        return context?.let {
            NotificationCompat.Builder(it, context.getString(R.string.channel_id))
                .setSmallIcon(R.drawable.cute_pink_dino)
                .setContentTitle(it.getString(R.string.notification_message_title))
                .setContentText(it.getString(R.string.notification_message_desc))
                .setContentIntent(intent)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(getSound(it))
                .setVibrate(getVibrationPattern())
                .setColor(getColor())
                .build()
        }
    }

    private fun getSound(context: Context): Uri? {
        return Uri.parse("android.resource://" + context.packageName + "/" + R.raw.alarm_skz)
    }

    private fun getVibrationPattern(): LongArray {
        return longArrayOf(100L, 200L, 0, 400L)
    }

    private fun getColor(): Int {
        return Color.BLUE
    }
}
