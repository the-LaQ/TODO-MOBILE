package ru.vinteno.lavka.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.vinteno.lavka.R
import ru.vinteno.lavka.data.Order

class OrdersAdapter : ListAdapter<Order, OrdersAdapter.VH>(Diff) {
    object Diff : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
        
        // Animate item entrance
        val animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.slide_up)
        animation.startOffset = position * 50L
        holder.itemView.startAnimation(animation)
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val address: TextView = itemView.findViewById(R.id.tv_delivery_address)
        private val items: TextView = itemView.findViewById(R.id.tv_items)

        fun bind(order: Order) {
            address.text = order.delivery_address
            items.text = order.items
        }
    }
}



