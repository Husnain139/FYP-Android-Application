package com.hstan.autoservify.ui.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hstan.autoservify.R
import com.hstan.autoservify.databinding.ItemServiceBinding
import com.hstan.autoservify.ui.ViewHolder.serviceViewHolder
import com.hstan.autoservify.ui.main.Shops.Services.Service

class ServiceAdapter(private var items: List<Service>) : RecyclerView.Adapter<serviceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): serviceViewHolder {
        val binding = ItemServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return serviceViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: serviceViewHolder, position: Int) {
        val service = items[position]

        // Bind data to layout views (camelCase binding IDs)
        holder.binding.ServiceTitle.text = service.name
        holder.binding.ServDesc.text = service.description
        holder.binding.ServPrice.text = "Rs. ${service.price}"
        holder.binding.serviceRating.text = "⭐ ${service.rating}"

        // If you want to support image later, add imageUrl to Service model
        Glide.with(holder.itemView.context)
            .load(R.drawable.logo) // For now, just show default logo (since Service model has no imageUrl)
            .error(R.drawable.logo)
            .placeholder(R.drawable.logo)
            .into(holder.binding.ServicePic)

        // Future: You can set edit/delete click listeners here
        holder.binding.editServ.setOnClickListener {
            // handle edit service
        }
        holder.binding.spDel.setOnClickListener {
            // handle delete service
        }
    }

    fun updateData(newItems: List<Service>) {
        items = newItems
        notifyDataSetChanged()
    }
}
