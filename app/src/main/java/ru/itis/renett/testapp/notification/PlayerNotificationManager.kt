package ru.itis.renett.testapp.notification

import android.app.NotificationChannel
import android.content.Context
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import ru.itis.renett.testapp.MainActivity
import ru.itis.renett.testapp.R
import ru.itis.renett.testapp.fragments.EXTRA_ID
import ru.itis.renett.testapp.fragments.EXTRA_PROGRESS
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
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = context.getString(R.string.notification_channel_desc)
            }.also {
                manager.createNotificationChannel(it)
            }
        }
    }

     fun createNotification(itemId: Int, player: MediaPlayer) {
        val track = TrackRepository.findTrackById(itemId)

        track?.let {
            val previousIntent = Intent(context, MusicPlayerService::class.java).apply {
                action = context.getString(R.string.previous)
            }
            val nextIntent = Intent(context, MusicPlayerService::class.java).apply {
                action = context.getString(R.string.next)
            }
            val resumeIntent = Intent(context, MusicPlayerService::class.java).apply {
                action = context.getString(R.string.resume)
            }
            val stopIntent = Intent(context, MusicPlayerService::class.java).apply {
                action = context.getString(R.string.stop)
            }
            val intentToStartActivity = Intent(context, MainActivity::class.java).apply {
                putExtra(EXTRA_ID, it.id)
                putExtra(EXTRA_PROGRESS, player.currentPosition)
            }

            val pendingIntentToStartActivity = PendingIntent.getActivity(
                context,
                7,
                intentToStartActivity,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            val previousPendingIntent = PendingIntent.getService(context,3, previousIntent,0)
            val nextPendingIntent = PendingIntent.getService(context,2, nextIntent,0)
            val resumePendingIntent = PendingIntent.getService(context,1, resumeIntent,0)
            val stopPendingIntent = PendingIntent.getService(context, 4, stopIntent, 0)

            val notification = NotificationCompat.Builder(context, context.getString(R.string.channel_id))
                .setSmallIcon(R.drawable.musical)
                .setContentTitle(track.title)
                .setContentText(track.artist)
                .setLargeIcon(BitmapFactory.decodeResource(context.resources,track.coverId))
                .setContentIntent(pendingIntentToStartActivity)
                .setDeleteIntent(stopPendingIntent)
                .addAction(R.drawable.ic_previous, context.getString(R.string.previous), previousPendingIntent)
                .addAction(R.drawable.ic_pause, context.getString(R.string.resume), resumePendingIntent)
                .addAction(R.drawable.ic_next, context.getString(R.string.next), nextPendingIntent)
                .addAction(R.drawable.ic_stop, context.getString(R.string.stop), stopPendingIntent)
                .setStyle(androidx.media.app.NotificationCompat.MediaStyle())
                .setShowWhen(false)
                .setAutoCancel(false)
                .setSilent(true)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build()
            manager.notify(notificationId, notification)
        }
    }

    fun getNotificationId(): Int = notificationId
}
