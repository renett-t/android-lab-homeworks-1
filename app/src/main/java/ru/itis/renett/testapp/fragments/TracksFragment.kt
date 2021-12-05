package ru.itis.renett.testapp.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import ru.itis.renett.testapp.R
import ru.itis.renett.testapp.databinding.FragmentTracksBinding
import ru.itis.renett.testapp.recyclerview.TrackAdapter
import ru.itis.renett.testapp.repository.TrackRepository

class TracksFragment : Fragment(R.layout.fragment_tracks) {

    private var binding: FragmentTracksBinding? = null
    private var trackAdapter: TrackAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTracksBinding.bind(view)

        trackAdapter = TrackAdapter(TrackRepository.tracks) {
            onTrackChosenAction(it)
        }

        view.findViewById<RecyclerView>(R.id.rv_tracks).run {
            adapter = trackAdapter
        }
    }

    private fun onTrackChosenAction(trackId: Int) {
        val bundle = Bundle().apply {
            putInt("id", trackId)
        }

        val options = NavOptions.Builder()
            .setLaunchSingleTop(false)
            .setEnterAnim(R.anim.enter_from_right)
            .setExitAnim(R.anim.fade_out)
            .setPopEnterAnim(R.anim.fade_in)
            .setPopExitAnim(R.anim.exit_to_right)
            .build()
        findNavController().navigate(R.id.action_tracksFragment_to_trackFragment, bundle, options)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        trackAdapter = null
    }
}
