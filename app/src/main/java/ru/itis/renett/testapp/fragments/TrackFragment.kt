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

const val EXTRA_ID = "id"
const val EXTRA_PROGRESS = "progress"

class TrackFragment: Fragment(R.layout.fragment_track) {

    private var binding: FragmentTrackBinding? = null
    private var binder: MusicPlayerService.MusicPlayerBinder? = null

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
           binder = service as? MusicPlayerService.MusicPlayerBinder
            arguments?.getInt(EXTRA_ID)?.let {
                val progress = arguments?.getInt(EXTRA_PROGRESS)
                if (progress != null) {
                    binder?.playTrackFromPosition(it, progress)
                } else {
                    binder?.playTrack(it)
                }
                initializeView(it)
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            binder = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.bindService(
            Intent(requireContext(), MusicPlayerService::class.java),
            connection,
            BIND_AUTO_CREATE
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTrackBinding.bind(view)

        initListeners()
    }

    private fun initListeners() {
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
            btnStop.setOnClickListener {
                stopPlayingSong()
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
    }

    private fun playPreviousSong() {
        binder?.playPreviousTrack()
        binder?.getCurrentTrack()?.id?.let {
            initializeView(it)
        }
    }

    private fun pauseOrPlayTrack() {
        if (binder?.isPlaying() == true) {
            binder?.pause()
            binding?.btnPausePlay?.setImageResource(R.drawable.play)
        } else {
            binder?.play()
            binding?.btnPausePlay?.setImageResource(R.drawable.pause)
        }
    }

    private fun stopPlayingSong() {
        binder?.stop()
        binding?.btnPausePlay?.setImageResource(R.drawable.play)
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
    }
}
