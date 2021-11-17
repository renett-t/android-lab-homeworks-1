package ru.itis.renett.testapp.viewpager

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class FlowerCardAdapter (
    private val list: List<FlowerCard>,
    private val onItemChosenAction: (Int) -> Unit
) : RecyclerView.Adapter<FlowerCardHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlowerCardHolder {
        return FlowerCardHolder.create(parent, onItemChosenAction)
    }

    override fun onBindViewHolder(holder: FlowerCardHolder, position: Int) {
        holder.bind(list[position].id)
    }

    override fun getItemCount() = list.size
}
