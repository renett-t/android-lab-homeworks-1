package ru.itis.renett.testapp.viewpager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.itis.renett.testapp.databinding.ItemFlowerCardBinding

class FlowerCardHolder (
    private val binding: ItemFlowerCardBinding,
    private val onItemChosenAction: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var card: FlowerCard? = null

    init {
        itemView.setOnClickListener {
            card?.run {
                onItemChosenAction(this.id)
            }
        }
    }

    fun bind(itemId: Int) {
        this.card = FlowerCardRepository.getFlowerCardById(itemId)
        with(binding) {
            card?.let {
                tvItemCardTitle.text = it.title
                tvItemCardDesc.text = it.descr
                vp2Images.adapter = ViewPagerAdapter(it.images)
            }
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onItemChosenAction: (Int) -> Unit
        ) = FlowerCardHolder (
            ItemFlowerCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onItemChosenAction)
    }
}
