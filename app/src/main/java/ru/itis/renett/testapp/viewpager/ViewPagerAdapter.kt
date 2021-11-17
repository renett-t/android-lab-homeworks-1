package ru.itis.renett.testapp.viewpager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.itis.renett.testapp.databinding.ViewpagerImageItemBinding

class ViewPagerAdapter(
    private val images: List<Int>,
) : RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {

    inner class ViewPagerViewHolder(
        private val binding: ViewpagerImageItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(imageId: Int) {
            with(binding) {
                ivItemImage.setImageResource(imageId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        return ViewPagerViewHolder(ViewpagerImageItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        val currentImage = images[position]
        holder.bind(currentImage)
    }

    override fun getItemCount() = images.size
}
