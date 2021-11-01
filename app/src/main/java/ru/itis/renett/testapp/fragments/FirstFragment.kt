package ru.itis.renett.testapp.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import ru.itis.renett.testapp.R
import ru.itis.renett.testapp.databinding.FragmentFirstBinding
import ru.itis.renett.testapp.repository.AlbumRepository
import ru.itis.renett.testapp.rvalbums.AlbumAdapter

class FirstFragment : Fragment(R.layout.fragment_first) {

    private var binding: FragmentFirstBinding? = null
    private var bookAdapter: AlbumAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentFirstBinding.bind(view)
        bookAdapter = AlbumAdapter(AlbumRepository.albumList) {
            showAlbumFragment(it)
        }

        view.findViewById<RecyclerView>(R.id.rv_albums).run {
            adapter = bookAdapter
        }
    }

    private fun showAlbumFragment(albumId: Int) {
        val bundle = Bundle().apply {
            putInt("albumId", albumId)
        }
        val options = NavOptions.Builder()
            .setLaunchSingleTop(false)
            .setEnterAnim(R.anim.enter_from_right)
            .setExitAnim(R.anim.fade_out)
            .setPopEnterAnim(R.anim.fade_in)
            .setPopExitAnim(R.anim.exit_to_right)
            .build()
        findNavController().navigate(R.id.action_firstFragment_to_albumFragment, bundle, options)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
