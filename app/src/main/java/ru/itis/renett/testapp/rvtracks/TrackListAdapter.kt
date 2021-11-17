package ru.itis.renett.testapp.rvtracks

import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.itis.renett.testapp.models.Track

class TrackListAdapter (
    private val onItemChosenAction: (Int) -> Unit,
    private val onDeleteItemAction: (Int) -> Unit
) : ListAdapter<Track, TrackHolder>(TrackDiffUtilItemCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrackHolder = TrackHolder.create(parent, onItemChosenAction, onDeleteItemAction)

    override fun onBindViewHolder(
        holder: TrackHolder,
        position: Int
    ) {
        holder.bind(getItem(position).id)
    }

    override fun onBindViewHolder(
        holder: TrackHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            payloads.last().takeIf { it is Bundle }?.let {
                holder.updateFields(it as Bundle)
            } ?: run {
                super.onBindViewHolder(holder, position, payloads)
            }
        }
    }

    override fun submitList(list: MutableList<Track>?) {
        super.submitList(if (list == null) null else ArrayList(list))
    }

}
