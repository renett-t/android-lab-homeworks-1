package ru.itis.renett.testapp.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ru.itis.renett.testapp.R
import ru.itis.renett.testapp.databinding.FragmentAlbumBinding
import ru.itis.renett.testapp.models.Album
import ru.itis.renett.testapp.repository.AlbumRepository
import ru.itis.renett.testapp.repository.TrackRepository
import ru.itis.renett.testapp.rvtracks.TrackAdapter

class AlbumFragment : Fragment(R.layout.fragment_album) {

    private var binding: FragmentAlbumBinding? = null
    private var trackAdapter: TrackAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAlbumBinding.bind(view)

        binding?.run {
            var foundAlbum : Album? = null

            arguments?.getInt("albumId")?.let { albumId ->
                foundAlbum = AlbumRepository.getAlbumById(albumId)

                trackAdapter = TrackAdapter(TrackRepository.getAlbumTracks(albumId)) {
                    showChosenTrack(it)
                }

                view.findViewById<RecyclerView>(R.id.rv_tracks).run {
                    adapter = trackAdapter
                }
            }

            foundAlbum?.let {
                ivItemAlbumCover.setImageResource(it.coverUrl)
                tvAlbumTitle.text = "Title: ${it.title}"
                tvAlbumArtist.text = "Artist(s): ${it.artist}"
                tvAlbumDate.text = "Year of publishing: ${it.date}"
            }
        }
    }

    private fun showChosenTrack(it: Int) {
        Snackbar.make(
            requireActivity().findViewById(R.id.fragment_container),
            "You've chosen: ${TrackRepository.getTrackById(it)}",
            Snackbar.LENGTH_LONG
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
