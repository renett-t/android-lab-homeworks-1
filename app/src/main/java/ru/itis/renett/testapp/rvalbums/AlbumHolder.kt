package ru.itis.renett.testapp.rvalbums

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.itis.renett.testapp.databinding.ItemAlbumBinding
import ru.itis.renett.testapp.models.Album
import ru.itis.renett.testapp.repository.AlbumRepository

class AlbumHolder (
    private val binding: ItemAlbumBinding,
    private val onItemChosenAction: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var albumToDisplay: Album? = null

    init {
        itemView.setOnClickListener {
            albumToDisplay?.run {
                onItemChosenAction(this.id)
            }
        }
    }

    fun bind(item: Album) {
        this.albumToDisplay = AlbumRepository.getAlbumById(item.id)
        with(binding) {
            ivItemAlbumCover.setImageResource(item.coverUrl)
            tvItemAlbumTitle.text = item.title
            tvItemAlbumArtist.text = item.artist
            tvItemAlbumDate.text = item.date
        }
    }

    companion object {
        fun create (
            parent: ViewGroup,
            onItemChosenAction: (Int) -> Unit
        ) = AlbumHolder (
            ItemAlbumBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onItemChosenAction)
    }
}
