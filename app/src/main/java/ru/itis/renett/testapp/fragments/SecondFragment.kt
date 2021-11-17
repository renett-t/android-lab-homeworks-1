package ru.itis.renett.testapp.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import ru.itis.renett.testapp.R
import ru.itis.renett.testapp.databinding.FragmentSecondBinding
import ru.itis.renett.testapp.models.Track
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

        with(binding) {
            this?.rvTracks?.run {
                adapter = trackListAdapter
                addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
            }

            this?.fabtnAdd?.setOnClickListener {
                addNewTrack()
            }
        }

        trackListAdapter?.submitList(TrackRepository.tracks)
    }

    private fun addNewTrack() {
        val dialogLayout = LayoutInflater.from(context).inflate(R.layout.dialog, null)
        createDialog(dialogLayout)
    }

    private fun createDialog(dialogLayout: View) {
        AlertDialog.Builder(requireContext())
            .setTitle("Добавить элемент")
            .setView(dialogLayout)
            .setPositiveButton("OK") {
                _,_ ->
                    val title = dialogLayout.findViewById<TextInputEditText>(R.id.tiet_title).text.toString()
                    if (title != "") {
                        val newTrack = Track(
                            0, title,
                            dialogLayout.findViewById<EditText>(R.id.et_artists).text.toString(),
                            R.drawable.cover6,
                            188
                        )
                        val index =
                            getIndexFromInput(dialogLayout.findViewById<EditText>(R.id.et_position).text.toString())
                        if (index > 0) {
                            TrackRepository.addNewTrack(index, newTrack)
                        } else {
                            TrackRepository.addNewTrack(newTrack)
                        }

                        updateTracksData()
                    }
            }
            .create()
            .show()
    }

    private fun getIndexFromInput(index: String?): Int {
        if (index != null && index != "") {
            val intInd = index.toInt()
            return if (intInd > 0 && intInd < TrackRepository.getSize()) intInd
            else -1
        }
        return -1;
    }

    private fun updateTracksData() {
        trackListAdapter?.submitList(TrackRepository.tracks)
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
