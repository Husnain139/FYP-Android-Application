package com.hstan.autoservify.ui.main.Shops.Services

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.hstan.autoservify.R
import com.hstan.autoservify.ui.Adapters.ServiceAdapter

class ServicesActivity : AppCompatActivity() {

    private lateinit var adapter: ServiceAdapter
    private lateinit var viewModel: ServiceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_services)

        val recyclerView = findViewById<RecyclerView>(R.id.services_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ServiceAdapter(mutableListOf())
        recyclerView.adapter = adapter

        // Init ViewModel
        viewModel = ViewModelProvider(this).get(ServiceViewModel::class.java)

        // Observe LiveData
        viewModel.services.observe(this) { services ->
            adapter.updateData(services)
        }

        // Load data
        viewModel.loadServices()

        // Add new service
        findViewById<ExtendedFloatingActionButton>(R.id.add_Service).setOnClickListener {
            startActivity(Intent(this, Add_Service_Activity::class.java))
        }
    }
}
