package ru.itis.renett.testapp.rvtracks

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import ru.itis.renett.testapp.models.Track

class TrackDiffUtilItemCallback : DiffUtil.ItemCallback<Track>() {
    override fun areItemsTheSame(
        oldItem: Track,
        newItem: Track
    ) = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Track,
        newItem: Track
    ) = oldItem == newItem

    override fun getChangePayload(oldItem: Track, newItem: Track): Any? {
        val bundle = Bundle()

        if (oldItem.title != newItem.title) {
            bundle.putString("TITLE", newItem.title)
        }
        if (oldItem.artist != newItem.artist) {
            bundle.putString("ARTIST", newItem.artist)
        }

        return bundle;
    }
}
