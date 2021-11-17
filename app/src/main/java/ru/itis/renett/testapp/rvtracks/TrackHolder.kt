package ru.itis.renett.testapp.rvtracks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ru.itis.renett.testapp.R
import ru.itis.renett.testapp.databinding.ItemTrackBinding
import ru.itis.renett.testapp.models.Track
import ru.itis.renett.testapp.repository.TrackRepository

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

    fun bind(itemId: Int) {
        this.trackToDisplay = TrackRepository.getTrackById(itemId)
        with(binding) {
            trackToDisplay?.let {
                ivItemTrackCover.setImageResource(it.coverUrl)
                tvItemTrackArtist.text = it.artist
                tvItemTrackTitle.text = it.title

                ivDelete.setOnClickListener {
                    println("trying to remove")
                }
            }
        }
    }

    fun updateFields(bundle: Bundle?) {
        with(binding) {
            if (bundle?.containsKey("TITLE") == true) {
                bundle.getString("TITLE").also {
                    tvItemTrackTitle.text = it
                }
            }
            if (bundle?.containsKey("ARTIST") == true) {
                bundle.getString("ARTIST").also {
                    tvItemTrackArtist.text = it
                }
            }
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
                false),
            onItemChosenAction)
    }
}
