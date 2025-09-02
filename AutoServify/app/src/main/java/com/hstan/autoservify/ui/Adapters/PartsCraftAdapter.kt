package com.hstan.autoservify.ui.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.hstan.autoservify.databinding.ItemPartscraftBinding
import com.hstan.autoservify.ui.main.Shops.SpareParts.PartsCraft
import com.hstan.autoservify.ui.main.Shops.SpareParts.Partscraftdetail
import com.hstan.autoservify.ui.ViewHolder.PartsCraftViewHolder

class PartsCraftAdapter(private var items: List<PartsCraft>) : RecyclerView.Adapter<PartsCraftViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartsCraftViewHolder {
        val binding = ItemPartscraftBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PartsCraftViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PartsCraftViewHolder, position: Int) {
        val part = items[position]
        val context = holder.itemView.context

        holder.binding.SpTitle.text = part.title
        holder.binding.SpDesc.text = part.description
        holder.binding.SpPrice.text = "Rs. ${part.price}"


        Glide.with(context)
            .load(part.image)
            .placeholder(com.hstan.autoservify.R.drawable.logo)
            .error(com.hstan.autoservify.R.drawable.logo)
            .into(holder.binding.SpPic)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, Partscraftdetail::class.java)
            intent.putExtra("data", Gson().toJson(part))
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateData(newItems: List<PartsCraft>) {
        items = newItems
        notifyDataSetChanged()
    }
}