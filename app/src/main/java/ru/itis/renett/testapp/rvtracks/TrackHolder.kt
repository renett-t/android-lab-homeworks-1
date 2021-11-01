package ru.itis.renett.testapp.rvtracks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.itis.renett.testapp.databinding.ItemTrackBinding
import ru.itis.renett.testapp.models.Track
import ru.itis.renett.testapp.repository.TrackRepository
import java.util.*

class TrackHolder (
    private val binding: ItemTrackBinding,
    private val onItemChosenAction: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var trackToDisplay: Track? = null

    init {
        itemView.setOnClickListener {
            trackToDisplay?.run {
                onItemChosenAction(this.id)
            }
        }
    }

    fun bind(item: Track) {
        this.trackToDisplay = TrackRepository.getTrackById(item.id)
        with(binding) {
            TrackRepository.getTrackCoverByAlbumId(item.albumId)?.let {
                ivItemTrackCover.setImageResource(
                    it
                )
            }
            tvItemTrackTitle.text = item.title
            tvItemTrackArtist.text = item.artist

            val minutes = (item.duration % 3600) / 60;
            val seconds = item.duration % 60;
            tvItemTrackDuration.text = String.format(Locale.ENGLISH, "%02d:%02d", minutes, seconds)
        }
    }

    companion object {
        fun create (
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
