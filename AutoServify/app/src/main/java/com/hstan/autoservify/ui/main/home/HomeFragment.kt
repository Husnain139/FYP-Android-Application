package com.hstan.autoservify.ui.main.home

import android.os.Bundle
import android.content.Intent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager

import com.hstan.autoservify.R
import com.hstan.autoservify.databinding.FragmentHomeBinding
import com.hstan.autoservify.ui.main.Shops.AddShopActivity
import com.hstan.autoservify.ui.main.Shops.Shop
import kotlinx.coroutines.launch
import com.hstan.autoservify.ui.Adapters.ShopAdapter



class HomeFragment : Fragment() {

    lateinit var adapter: ShopAdapter
    val items=ArrayList< Shop>()
    lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: HomeFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = HomeFragmentViewModel()

        adapter= ShopAdapter(items)
        binding.recyclerview.adapter=adapter
        binding.recyclerview.layoutManager= LinearLayoutManager(context)

        lifecycleScope.launch {
            viewModel.failureMessage.collect {
                it?.let {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.data.collect {
                it?.let {
                    items.clear()
                    items.addAll(it)
                    adapter.notifyDataSetChanged()
                }
            }
        }

        binding.fabAddShop.setOnClickListener {
            startActivity(Intent(requireContext(), AddShopActivity::class.java))
        }

    }
}