package ru.itis.renett.testapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import ru.itis.renett.testapp.R
import ru.itis.renett.testapp.databinding.FragmentSecondBinding
import ru.itis.renett.testapp.models.Track
import ru.itis.renett.testapp.repository.TrackRepository
import ru.itis.renett.testapp.rvtracks.TrackListAdapter
import ru.itis.renett.testapp.swipe.SwipeToDeleteCallback

class SecondFragment : Fragment(R.layout.fragment_second) {
    private var binding: FragmentSecondBinding? = null
    private var trackListAdapter: TrackListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSecondBinding.bind(view)

        trackListAdapter = TrackListAdapter({showMessage(it)}, {deleteItemById(it)})

        with(binding) {
            this?.rvTracks?.run {
                adapter = trackListAdapter
                addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
            }

            val ith = ItemTouchHelper(SwipeToDeleteCallback(){
                onSwipeToDeleteAction(it)
            })
            ith.attachToRecyclerView(this?.rvTracks);

            this?.fabtnAdd?.setOnClickListener {
                inflateDialogToAddItem()
            }
        }

        trackListAdapter?.submitList(TrackRepository.tracks)
    }

    private fun updateTracksData(newList: MutableList<Track>) {
        trackListAdapter?.submitList(newList)
    }

    private fun inflateDialogToAddItem() {
        val dialogLayout = LayoutInflater.from(context).inflate(R.layout.dialog, null)
        createDialog(dialogLayout)
    }

    private fun createDialog(dialogLayout: View) {
        AlertDialog.Builder(requireContext())
            .setTitle("Добавить элемент")
            .setView(dialogLayout)
            .setPositiveButton("OK") {
                _,_ -> createNewItemFromInputs(dialogLayout)
            }
            .setNegativeButton("CANCEL") {
                    _,_ -> showMessage("Действие отменено")
            }
            .create()
            .show()
    }

    private fun createNewItemFromInputs(dialogLayout: View) {
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

            updateTracksData(TrackRepository.tracks)
            showMessage("Новый элемент был создан")
        }
    }

    private fun getIndexFromInput(index: String?): Int {
        if (index != null && index != "") {
            val intInd = index.toInt()
            return if (intInd > 0 && intInd < TrackRepository.getSize()) intInd
            else -1
        }
        return -1;
    }

    private fun deleteItemById(itemId: Int) {
        val itemToDelete = TrackRepository.getTrackById(itemId)
        TrackRepository.removeTrackById(itemId)
        updateTracksData(TrackRepository.tracks)
        showMessage("${itemToDelete?.title} был удалён из списка")
    }

    private fun onSwipeToDeleteAction(adapterPosition: Int) {
        val currentList = trackListAdapter?.currentList
        if (currentList != null) {
            val itemToDelete = currentList[adapterPosition]
            TrackRepository.removeTrackById(itemToDelete.id)
            updateTracksData(TrackRepository.tracks)
            showMessage("${itemToDelete.title} был удалён из списка")
        }
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

    private fun showMessage(message: String) {
        Snackbar.make(
            requireActivity().findViewById(R.id.fragment_container),
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
