package ru.itis.renett.testapp.media

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.navigation.NavDeepLinkBuilder
import ru.itis.renett.testapp.MainActivity
import ru.itis.renett.testapp.R
import ru.itis.renett.testapp.exception.MusicPlayerException
import ru.itis.renett.testapp.fragments.EXTRA_ID
import ru.itis.renett.testapp.models.Track
import ru.itis.renett.testapp.notification.PlayerNotificationManager
import ru.itis.renett.testapp.repository.TrackRepository

class MusicPlayerService : Service() {
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var notificationManager: PlayerNotificationManager
    private lateinit var trackList: List<Track>
    private var currentTrackId: Int = -1

    override fun onBind(intent: Intent?): IBinder = MusicPlayerBinder()

    inner class MusicPlayerBinder : Binder() {
        fun playTrack(trackId: Int) = this@MusicPlayerService.playTrack(trackId)
        fun play() = this@MusicPlayerService.playCurrentSong()
        fun pause() = this@MusicPlayerService.pauseCurrentSong()
        fun stop() = this@MusicPlayerService.stopPlayingCurrentSong()
        fun playNextTrack() = this@MusicPlayerService.playNextSong()
        fun playPreviousTrack() = this@MusicPlayerService.playPreviousSong()
        fun getCurrentTrack(): Track? = this@MusicPlayerService.getCurrentTrack()
        fun isPlaying(): Boolean = this@MusicPlayerService.isPlaying()
        fun playTrackFromPosition(id: Int, progress: Int) = this@MusicPlayerService.playTrackFromPosition(id, progress)
    }

    private fun playTrackFromPosition(id: Int, progress: Int) {
        playTrack(id)
        playCurrentFromPosition(progress)
    }

    private fun playCurrentFromPosition(progress: Int) = mediaPlayer.seekTo(progress)

    override fun onCreate() {
        super.onCreate()
        trackList = TrackRepository.getTracks()
        notificationManager = PlayerNotificationManager(this)
        mediaPlayer = MediaPlayer()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action) {
            this.getString(R.string.previous) -> playPreviousSong()
            this.getString(R.string.next) -> playNextSong()
            this.getString(R.string.resume) -> {
                if (mediaPlayer.isPlaying) pauseCurrentSong() else playCurrentSong()
            }
            this.getString(R.string.stop) -> stopPlayingCurrentSong()
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

    private fun stopPlayingCurrentSong() {
        mediaPlayer.stop()
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

            notificationManager.createNotification(trackId, mediaPlayer)
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
