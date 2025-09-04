package com.hstan.autoservify.ui.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hstan.autoservify.R
import com.hstan.autoservify.databinding.ItemOrderBinding
import com.hstan.autoservify.ui.Order
import com.hstan.autoservify.ui.ViewHolder.OrderViewHolder

class OrderAdapter(
    private var items: List<Order>,
    private val onViewClick: ((Order) -> Unit)? = null,
    private val onCancelClick: ((Order) -> Unit)? = null
) : RecyclerView.Adapter<OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = items[position]

        // Title and image from PartsCraft inside Order
        holder.binding.orderItemTitle.text = order.item?.title ?: "Item"
        holder.binding.orderQty.text = "Qty: ${order.quantity}"
        holder.binding.orderPrice.text = "Rs. ${order.item?.price ?: 0}"
        holder.binding.orderStatus.text = order.status.ifBlank { "pending" }
        holder.binding.orderDate.text = order.orderDate

        Glide.with(holder.itemView.context)
            .load(order.item?.image)
            .placeholder(R.drawable.logo)
            .error(R.drawable.logo)
            .into(holder.binding.orderItemImage)

        holder.binding.orderView.setOnClickListener { onViewClick?.invoke(order) }
        holder.binding.orderCancel.setOnClickListener { onCancelClick?.invoke(order) }

        // Optional: click anywhere to view
        holder.itemView.setOnClickListener { onViewClick?.invoke(order) }
    }

    fun updateData(newItems: List<Order>) {
        items = newItems
        notifyDataSetChanged()
    }
}