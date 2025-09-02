package com.hstan.autoservify.ui.main.Shops.SpareParts

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.hstan.autoservify.R
import com.hstan.autoservify.ui.Adapters.PartsCraftAdapter

class PartsCraftActivity : AppCompatActivity() {

    private lateinit var adapter: PartsCraftAdapter
    private lateinit var viewModel: PartsCraftViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partscraft)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PartsCraftAdapter(mutableListOf())
        recyclerView.adapter = adapter

        // Init ViewModel
        viewModel = ViewModelProvider(this).get(PartsCraftViewModel::class.java)

        // Observe LiveData
        viewModel.partsCrafts.observe(this) { partsCrafts ->
            adapter.updateData(partsCrafts)
        }

        // Load data
        viewModel.loadPartsCrafts()

        // Add new spare part
        findViewById<ExtendedFloatingActionButton>(R.id.add_SpareParts).setOnClickListener {
            startActivity(Intent(this, Addpartscraft::class.java))
        }
    }
}