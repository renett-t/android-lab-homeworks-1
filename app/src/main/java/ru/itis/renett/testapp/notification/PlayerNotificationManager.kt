package ru.itis.renett.testapp.notification

import android.app.Notification
import android.app.NotificationChannel
import android.content.Context
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import ru.itis.renett.testapp.R
import ru.itis.renett.testapp.fragments.TrackFragment
import ru.itis.renett.testapp.media.MusicPlayerService
import ru.itis.renett.testapp.repository.TrackRepository

class PlayerNotificationManager(
    private val context: Context
) {

    private val manager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
    private val notificationId = 11

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                context.getString(R.string.channel_id),
                context.getString(R.string.notification_channel_title),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = context.getString(R.string.notification_channel_desc)
            }.also {
                manager.createNotificationChannel(it)
            }
        }
    }

     fun createNotification(itemId: Int) {
        val track = TrackRepository.findTrackById(itemId)
        val intentToStartActivity = Intent(context, TrackFragment::class.java)

        track?.let {
            val previousIntent = Intent(context, MusicPlayerService::class.java).apply {
                action = context.getString(R.string.previous)
            }
            val resumeIntent = Intent(context, MusicPlayerService::class.java).apply {
                action = context.getString(R.string.resume)
            }
            val nextIntent = Intent(context, MusicPlayerService::class.java).apply {
                action = context.getString(R.string.next)
            }

            val previousPendingIntent = PendingIntent.getService(context,0, previousIntent,0)
            val resumePendingIntent = PendingIntent.getService(context,1, resumeIntent,0)
            val nextPendingIntent = PendingIntent.getService(context,2, nextIntent,0)

            val cover = BitmapFactory.decodeResource(context.resources,track.coverId)
            val notification = NotificationCompat.Builder(context, context.getString(R.string.channel_id))
                .setSmallIcon(R.drawable.musical)
                .setContentTitle(track.title)
                .setContentText(track.artist)
                .setLargeIcon(cover)
                .addAction(R.drawable.previous, context.getString(R.string.previous), previousPendingIntent)
                .addAction(R.drawable.pause, context.getString(R.string.pause), resumePendingIntent)
                .addAction(R.drawable.next, context.getString(R.string.next), nextPendingIntent )
                .setContentIntent(PendingIntent.getActivity(
                    context,
                    0,
                    intentToStartActivity,
                    PendingIntent.FLAG_UPDATE_CURRENT
                ))
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build()

            manager.notify(notificationId, notification)
        }
    }
}

