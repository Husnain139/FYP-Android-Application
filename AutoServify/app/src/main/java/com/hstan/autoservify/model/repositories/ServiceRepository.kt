package com.hstan.autoservify.model.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.hstan.autoservify.ui.main.Shops.Services.Service
import kotlinx.coroutines.tasks.await

class ServiceRepository {
    private val serviceCollection = FirebaseFirestore.getInstance().collection("Services")

    suspend fun saveService(service: Service): Boolean {
        return try {
            val doc = serviceCollection.document()
            service.id = doc.id
            doc.set(service).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getServices(): List<Service> {
        return try {
            val snapshot = serviceCollection.get().await()
            snapshot.toObjects(Service::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }
}
