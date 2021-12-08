package ru.itis.renett.testapp.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
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

        trackAdapter = TrackAdapter(TrackRepository.getTracks()) {
            onTrackChosenAction(it)
        }

        binding?.rvTracks?.run {
            adapter = trackAdapter
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun onTrackChosenAction(trackId: Int) {
        val bundle = Bundle().apply {
            putInt(EXTRA_ID, trackId)
        }
        val newFragment = OneTrackFragment()
        newFragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.enter_from_right,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.exit_to_right
            )
            .replace(R.id.fragment_container, newFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        trackAdapter = null
    }
}
