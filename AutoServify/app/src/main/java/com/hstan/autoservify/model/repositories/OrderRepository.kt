package com.hstan.autoservify.model.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import com.hstan.autoservify.ui.Order
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class OrderRepository {
    val orderCollection = FirebaseFirestore.getInstance().collection("orders")

    suspend fun saveOrder(order: Order): Result<Boolean> {
        return try {
            val document = orderCollection.document()
            order.id = document.id
            document.set(order).await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateOrder(order: Order): Result<Boolean> {
        return try {
            val document = orderCollection.document(order.id)
            document.set(order).await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getOrders() =
        orderCollection.snapshots().map { it.toObjects(Order::class.java) }
}