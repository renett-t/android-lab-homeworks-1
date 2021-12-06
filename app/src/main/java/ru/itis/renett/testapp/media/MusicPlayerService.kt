package ru.itis.renett.testapp.media

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import ru.itis.renett.testapp.R
import ru.itis.renett.testapp.exception.MusicPlayerException
import ru.itis.renett.testapp.models.Track
import ru.itis.renett.testapp.notification.PlayerNotificationManager
import ru.itis.renett.testapp.repository.TrackRepository

class MusicPlayerService : Service() {
    private var mediaPlayer: MediaPlayer = MediaPlayer()
    private lateinit var notificationManager: PlayerNotificationManager
    private lateinit var trackList: List<Track>
    private var currentTrackId: Int = -1

    override fun onBind(intent: Intent?): IBinder = MusicPlayerBinder()

    inner class MusicPlayerBinder : Binder() {
        fun playTrack(trackId: Int) = this@MusicPlayerService.playTrack(trackId)
        fun play() = this@MusicPlayerService.playCurrentSong()
        fun pause() = this@MusicPlayerService.pauseCurrentSong()
        fun playNextTrack() = this@MusicPlayerService.playNextSong()
        fun playPreviousTrack() = this@MusicPlayerService.playPreviousSong()
        fun getCurrentTrack(): Track? = this@MusicPlayerService.getCurrentTrack()
        fun isPlaying(): Boolean = this@MusicPlayerService.isPlaying()
    }

    override fun onCreate() {
        super.onCreate()
        trackList = TrackRepository.getTracks()
        notificationManager = PlayerNotificationManager(this)
        Log.e("MUSIC", "INITIALIZATION DONE")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action) {
            this.getString(R.string.previous) -> playPreviousSong()
            this.getString(R.string.next) -> playNextSong()
            this.getString(R.string.resume) -> {
                if (mediaPlayer.isPlaying) pauseCurrentSong() else playCurrentSong()
            }
            this.getString(R.string.stop_service) -> stopSelf()
        }

        return START_REDELIVER_INTENT
    }

    private fun pauseCurrentSong() {
        mediaPlayer.pause()
    }

    private fun playCurrentSong() {
        mediaPlayer.start()
    }

    private fun getCurrentTrack(): Track? {
        return if (currentTrackId in 0..trackList.size)
            trackList[currentTrackId]
        else
            null
    }

    private fun playPreviousSong() {
        currentTrackId =
            if (currentTrackId > -1) {
                if (currentTrackId == 0)
                    trackList.size - 1
                else
                    currentTrackId - 1
            } else {
                0
            }
        playTrack(currentTrackId)
    }

    private fun playNextSong() {
        currentTrackId =
            if (currentTrackId > -1) {
                if (currentTrackId == trackList.size - 1)
                    0
                else
                    currentTrackId + 1
            } else {
                0
            }
        playTrack(currentTrackId)
    }

    private fun playTrack(trackId: Int) {
        Log.e("MUSIC", "PLAY $trackId")

        if (mediaPlayer.isPlaying)
            mediaPlayer.stop()

        if (checkIdIsCorrect(trackId)) {
            currentTrackId = trackId

            mediaPlayer = MediaPlayer.create(applicationContext, trackList[currentTrackId].rawFileId)
            mediaPlayer.run {
                start()
                setOnCompletionListener {
                    playNextSong()
                }
            }

            notificationManager.createNotification(trackId)
//            startForeground(notificationManager.getNotificationId(), notificationManager.createNotification(trackId))
        } else {
            throw MusicPlayerException("Wrong id = $trackId for a track. Cannot play.")
        }
    }

    private fun isPlaying(): Boolean = mediaPlayer.isPlaying

    private fun checkIdIsCorrect(id: Int): Boolean {
        return id in trackList.indices
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}
