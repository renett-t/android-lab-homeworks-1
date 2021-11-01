package ru.itis.renett.testapp.rvalbums

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.itis.renett.testapp.models.Album

class AlbumAdapter(
    private val list: List<Album>,
    private val onItemChosenAction: (Int) -> Unit
    ) : RecyclerView.Adapter<AlbumHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumHolder {
        return AlbumHolder.create(parent, onItemChosenAction)
    }

    override fun onBindViewHolder(holder: AlbumHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}
