package com.hstan.autoservify.ui.main.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hstan.autoservify.databinding.FragmentOrdersBinding
import com.hstan.autoservify.ui.Adapters.OrderAdapter
import com.hstan.autoservify.ui.Order
import kotlinx.coroutines.launch

class OrdersFragment : Fragment() {

    lateinit var binding: FragmentOrdersBinding
    lateinit var viewModel: OrdersFragmentViewModel
    lateinit var adapter: OrderAdapter
    private val items = ArrayList<Order>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = OrderAdapter(
            items,
            onViewClick = { /* navigate to order detail if needed */ },
            onCancelClick = { order -> viewModel.cancelOrder(order) }
        )
        binding.recyclerview.layoutManager = LinearLayoutManager(context)
        binding.recyclerview.adapter = adapter

        viewModel = OrdersFragmentViewModel()

        lifecycleScope.launch {
            viewModel.failureMessage.collect { msg ->
                msg?.let { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() }
            }
        }
        lifecycleScope.launch {
            viewModel.data.collect { list ->
                list?.let {
                    items.clear()
                    items.addAll(it)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }
}