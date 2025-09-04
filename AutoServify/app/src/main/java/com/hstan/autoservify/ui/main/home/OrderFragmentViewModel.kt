package com.hstan.autoservify.ui.main.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hstan.autoservify.model.repositories.OrderRepository
import com.hstan.autoservify.ui.Order
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class OrdersFragmentViewModel : ViewModel() {

    private val ordersRepository = OrderRepository()

    val failureMessage = MutableStateFlow<String?>(null)
    val data = MutableStateFlow<List<Order>?>(null)
    val isUpdating = MutableStateFlow(false)

    init {
        readOrders()
    }

    fun readOrders() {
        viewModelScope.launch {
            ordersRepository.getOrders()
                .catch { failureMessage.value = it.message }
                .collect { data.value = it }
        }
    }

    fun cancelOrder(order: Order) {
        viewModelScope.launch {
            try {
                isUpdating.value = true
                order.status = "cancelled"
                val result = ordersRepository.updateOrder(order)
                if (result.isFailure) {
                    failureMessage.value = result.exceptionOrNull()?.message
                }
            } finally {
                isUpdating.value = false
            }
        }
    }
}