package ru.itis.renett.testapp.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.itis.renett.testapp.R
import ru.itis.renett.testapp.databinding.FragmentAlbumBinding
import ru.itis.renett.testapp.models.Album
import ru.itis.renett.testapp.repository.AlbumRepository

class AlbumFragment : Fragment(R.layout.fragment_album) {

    private var binding: FragmentAlbumBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAlbumBinding.bind(view)

        binding?.run {
            var foundAlbum : Album? = null

            arguments?.getInt("albumId")?.let {
                foundAlbum = AlbumRepository.getAlbumById(it)
            }

            foundAlbum?.let {
                ivAlbumCover.setImageResource(it.coverUrl)
                tvAlbumTitle.text = it.title
                tvAlbumArtist.text = it.artist
                tvAlbumDate.text = it.date
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
