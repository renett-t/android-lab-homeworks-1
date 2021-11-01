package ru.itis.renett.testapp.rvtracks

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.itis.renett.testapp.models.Track

class TrackAdapter (
    private val list: List<Track>,
    private val onItemChosenAction: (Int) -> Unit
) : RecyclerView.Adapter<TrackHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackHolder {
        return TrackHolder.create(parent, onItemChosenAction)
    }

    override fun onBindViewHolder(holder: TrackHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size
}
