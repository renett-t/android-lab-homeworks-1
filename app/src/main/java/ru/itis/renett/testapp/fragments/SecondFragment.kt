package ru.itis.renett.testapp.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import ru.itis.renett.testapp.R
import ru.itis.renett.testapp.databinding.FragmentSecondBinding
import ru.itis.renett.testapp.repository.TrackRepository
import ru.itis.renett.testapp.rvtracks.TrackListAdapter

class SecondFragment : Fragment(R.layout.fragment_second) {
    private var binding: FragmentSecondBinding? = null
    private var trackListAdapter: TrackListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSecondBinding.bind(view)

        trackListAdapter = TrackListAdapter() {
            showMessage(it)
        }

        view.findViewById<RecyclerView>(R.id.rv_tracks).run {
            adapter = trackListAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        }

        view.findViewById<FloatingActionButton>(R.id.fabtn_add).setOnClickListener {
            addNewTrack()
        }

        trackListAdapter?.submitList(TrackRepository.tracks)
        TODO("DELETE BUTTON")
    }

    private fun addNewTrack() {
        TODO("https://guides.codepath.com/android/using-dialogfragment")
    }

    private fun showMessage(trackId: Int) {
        TrackRepository.getTrackById(trackId)?.title?.let {
            Snackbar.make(
                requireActivity().findViewById(R.id.fragment_container),
                it,
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
