package ru.itis.renett.testapp.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ru.itis.renett.testapp.R
import ru.itis.renett.testapp.databinding.FragmentThirdBinding
import ru.itis.renett.testapp.repository.TrackRepository
import ru.itis.renett.testapp.rvtracks.TrackListAdapter

class ThirdFragment : Fragment(R.layout.fragment_third) {
    private var binding: FragmentThirdBinding? = null
    private var trackListAdapter: TrackListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentThirdBinding.bind(view)

        trackListAdapter = TrackListAdapter() {
            showMessage(it)
        }

        view.findViewById<RecyclerView>(R.id.rv_card_tracks).run {
            adapter = trackListAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        }

        trackListAdapter?.submitList(TrackRepository.tracks)
        TODO("viewpager2");
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
