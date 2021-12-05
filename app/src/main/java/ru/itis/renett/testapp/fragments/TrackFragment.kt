package ru.itis.renett.testapp.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import ru.itis.renett.testapp.R
import ru.itis.renett.testapp.databinding.FragmentTrackBinding
import ru.itis.renett.testapp.repository.TrackRepository

class TrackFragment: Fragment(R.layout.fragment_track) {

    private var binding: FragmentTrackBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTrackBinding.bind(view)

        arguments?.getInt("id")?.let {
            initilizeView(it)
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

    private fun initilizeView(trackId: Int) {
        var repoTrack = trackId.let { TrackRepository.findTrackById(it) }
        binding?.run {
            repoTrack?.let { track ->
                ivCover.setImageResource(track.coverId)
                tvArtist.text = track.artist
                tvTitle.text = track.title
            }
        }
    }

    private fun playNextTrack() {
        showMessage("playing next track")
    }

    private fun playPreviousSong() {
        showMessage("playing previous track")
    }

    private fun pauseOrPlayTrack() {
        showMessage("pause or resume playing track, change image")
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
