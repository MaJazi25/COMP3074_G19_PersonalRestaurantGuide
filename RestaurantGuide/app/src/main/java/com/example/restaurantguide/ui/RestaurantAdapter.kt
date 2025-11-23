package com.example.restaurantguide.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantguide.databinding.ItemRestaurantBinding
import com.example.restaurantguide.model.Restaurant

class RestaurantAdapter(
    private val items: MutableList<Restaurant>,
    private val onClick: (Restaurant) -> Unit
) : RecyclerView.Adapter<RestaurantAdapter.VH>() {

    inner class VH(val vb: ItemRestaurantBinding) : RecyclerView.ViewHolder(vb.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val vb = ItemRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(vb)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val r = items[position]
        holder.vb.tvName.text = r.name
        holder.vb.tvAddress.text = r.address
        holder.vb.tvTags.text = r.tags.joinToString(", ")
        holder.vb.ratingBar.rating = r.rating
        holder.itemView.setOnClickListener { onClick(r) }
    }

    override fun getItemCount() = items.size

    fun replaceAll(newItems: List<Restaurant>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}
