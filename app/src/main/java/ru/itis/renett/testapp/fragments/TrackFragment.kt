package ru.itis.renett.testapp.fragments

import android.content.ComponentName
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import ru.itis.renett.testapp.R
import ru.itis.renett.testapp.databinding.FragmentTrackBinding
import ru.itis.renett.testapp.media.MusicPlayerService
import ru.itis.renett.testapp.repository.TrackRepository

class TrackFragment: Fragment(R.layout.fragment_track) {

    private var binding: FragmentTrackBinding? = null
    private var binder: MusicPlayerService.MusicPlayerBinder? = null

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
           binder = service as? MusicPlayerService.MusicPlayerBinder
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            binder = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().bindService(
            Intent(requireContext(), MusicPlayerService::class.java),
            connection,
            BIND_AUTO_CREATE
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTrackBinding.bind(view)

        arguments?.getInt("id")?.let {
            binder?.playTrack(it)
            initializeView(it)
        }

        binding?.run {
            btnNext.setOnClickListener {
                playNextTrack()
            }

            btnPausePlay.setOnClickListener {
                pauseOrPlayTrack()
            }

            btnPrevious.setOnClickListener {
                playPreviousSong()
            }
        }
    }

    private fun initializeView(trackId: Int) {
        val repoTrack = trackId.let { TrackRepository.findTrackById(it) }
        binding?.run {
            repoTrack?.let { track ->
                ivCover.setImageResource(track.coverId)
                tvArtist.text = track.artist
                tvTitle.text = track.title
            }
            btnPausePlay.setImageResource(R.drawable.pause)
        }
    }

    private fun playNextTrack() {
        binder?.playNextTrack()
        binder?.getCurrentTrack()?.id?.let {
            initializeView(it)
        }
        showMessage("playing next track")
    }

    private fun playPreviousSong() {
        binder?.playPreviousTrack()
        binder?.getCurrentTrack()?.id?.let {
            initializeView(it)
        }
        showMessage("playing previous track")
    }

    private fun pauseOrPlayTrack() {
        if (binder?.isPlaying() == true) {
            binder?.pause()
            binding?.btnPausePlay?.setImageResource(R.drawable.play)
            showMessage("paused, change image")
        } else {
            binder?.play()
            binding?.btnPausePlay?.setImageResource(R.drawable.pause)
            showMessage("resumed, change image")
        }
    }

    private fun showMessage(message: String) {
        Snackbar.make(
            requireActivity().findViewById(R.id.fragment_container),
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        requireActivity().unbindService(connection)
    }
}
