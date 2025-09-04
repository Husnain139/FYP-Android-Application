package com.hstan.autoservify.ui.main.Shops.SpareParts

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.hstan.autoservify.databinding.ActivityPartscraftdetailBinding
import com.hstan.autoservify.ui.orders.CreateOrderActivity

class Partscraftdetail :  AppCompatActivity() {

    lateinit var binding: ActivityPartscraftdetailBinding;
    lateinit var partCraft: PartsCraft;

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPartscraftdetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        partCraft = Gson().fromJson(intent.getStringExtra("data"), PartsCraft::class.java)

        binding.spTitle.text = partCraft.title
        binding.spDesc.text = partCraft.description  // Changed from description to sp_desc
		binding.spPrice.text = "Rs ${partCraft.price}"


//        binding.productImage.setImageResource(partCraft.image.toInt())


        binding.AddtoCartButton .setOnClickListener {
          startActivity(Intent(this, CreateOrderActivity::class.java).putExtra("data", Gson().toJson(partCraft)))
           finish()
        }
    }
}
