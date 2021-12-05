package ru.itis.renett.testapp.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.itis.renett.testapp.databinding.ItemTrackBinding
import ru.itis.renett.testapp.models.Track
import ru.itis.renett.testapp.repository.TrackRepository
import java.util.*

class TrackHolder(
    private val binding: ItemTrackBinding,
    private val onItemChosenAction: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var currentTrack: Track? = null

    init {
        itemView.setOnClickListener {
            currentTrack?.run {
                onItemChosenAction(this.id)
            }
        }
    }

    fun bind(itemId: Int) {
        this.currentTrack = TrackRepository.findTrackById(itemId)

        currentTrack?.let { track ->
            with(binding) {
                tvItemTrackTitle.text = track.title
                tvItemTrackArtist.text = track.artist
                ivItemTrackCover.setImageResource(track.coverId)

                val minutes = (track.duration % 3600) / 60
                val seconds = track.duration % 60
                tvItemTrackDuration.text = String.format(Locale.ENGLISH, "%02d:%02d", minutes, seconds)
            }
        }
    }

    companion object {
        fun createHolder(
            parent: ViewGroup,
            onItemChosenAction: (Int) -> Unit
        ) = TrackHolder (
            ItemTrackBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onItemChosenAction)
    }
}
