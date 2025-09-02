package com.hstan.autoservify.ui.main.Shops.Services

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hstan.autoservify.model.repositories.ServiceRepository
import kotlinx.coroutines.launch

class ServiceViewModel : ViewModel() {

    private val repository = ServiceRepository()

    private val _services = MutableLiveData<List<Service>>()
    val services: LiveData<List<Service>> = _services

    private val _isSuccessfullySaved = MutableLiveData<Boolean?>()
    val isSuccessfullySaved: LiveData<Boolean?> = _isSuccessfullySaved

    private val _failureMessage = MutableLiveData<String?>()
    val failureMessage: LiveData<String?> = _failureMessage

    fun addService(service: Service) {
        viewModelScope.launch {
            try {
                val success = repository.saveService(service)
                _isSuccessfullySaved.value = success
                if (success) {
                    loadServices()
                } else {
                    _failureMessage.value = "Failed to save service"
                }
            } catch (e: Exception) {
                Log.e("ServiceViewModel", "Error saving service: ${e.message}")
                _failureMessage.value = e.message
            }
        }
    }

    fun loadServices() {
        viewModelScope.launch {
            try {
                val result = repository.getServices()
                _services.value = result
            } catch (e: Exception) {
                _failureMessage.value = e.message
            }
        }
    }
}
