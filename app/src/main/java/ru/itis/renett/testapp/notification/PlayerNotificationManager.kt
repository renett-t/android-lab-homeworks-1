package ru.itis.renett.testapp.notification

import android.app.Notification
import android.app.NotificationChannel
import android.content.Context
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import ru.itis.renett.testapp.MainActivity
import ru.itis.renett.testapp.R
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

     fun createNotification(itemId: Int): Notification? {
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
            val stopServiceIntent = Intent(context, MusicPlayerService::class.java).apply {
                action = context.getString(R.string.stop_service)
            }

            val intentToOpenFragment = NavDeepLinkBuilder(context)
                .setComponentName(MainActivity::class.java)
                .setGraph(R.navigation.nav_graph)
                .setDestination(R.id.trackFragment)
                .setArguments(Bundle().apply {
                    putInt("id", track.id)
                })
                .createPendingIntent()

            val previousPendingIntent = PendingIntent.getService(context,3, previousIntent,0)
            val nextPendingIntent = PendingIntent.getService(context,2, nextIntent,0)
            val resumePendingIntent = PendingIntent.getService(context,1, resumeIntent,0)
            val stopServicePendingIntent = PendingIntent.getService(context, 0, stopServiceIntent, 0)
//            val mediaSession = MediaSession(context, "MusicPlayerService")
//            val mediaStyle = Notification.MediaStyle().setMediaSession(mediaSession.sessionToken)

            val cover = BitmapFactory.decodeResource(context.resources,track.coverId)
            val notification = NotificationCompat.Builder(context, context.getString(R.string.channel_id))
                .setSmallIcon(R.drawable.musical)
                .setContentTitle(track.title)
                .setContentText(track.artist)
                .setLargeIcon(cover)
                .setContentIntent(intentToOpenFragment)
                .addAction(R.drawable.ic_previous, context.getString(R.string.previous), previousPendingIntent)
                .addAction(R.drawable.ic_pause, context.getString(R.string.pause), resumePendingIntent)
                .addAction(R.drawable.ic_next, context.getString(R.string.next), nextPendingIntent)
//                .setDeleteIntent(stopServicePendingIntent)
//                .setStyle(mediaStyle)
                .setShowWhen(false)
                .setAutoCancel(false)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build()
            manager.notify(notificationId, notification)
            return notification
        }
         return null
    }

    fun getNotificationId(): Int = notificationId
}
