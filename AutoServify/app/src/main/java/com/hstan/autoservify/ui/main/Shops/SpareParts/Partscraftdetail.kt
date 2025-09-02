package com.hstan.autoservify.ui.main.Shops.SpareParts

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import com.hstan.autoservify.R
import com.hstan.autoservify.databinding.ActivityPartscraftdetailBinding
import com.hstan.autoservify.ui.main.Shops.SpareParts.PartsCraft

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

        // You need to add a price TextView to your layout first, or remove this line
        // binding.price.text = "Rs ${partCraft.price}"


//        binding.productImage.setImageResource(partCraft.image.toInt())


//        binding.orderButton.setOnClickListener {
//            startActivity(Intent(this, createorder::class.java).putExtra("data", Gson().toJson(partCraft)))
//            finish()
//        }
    }
}
