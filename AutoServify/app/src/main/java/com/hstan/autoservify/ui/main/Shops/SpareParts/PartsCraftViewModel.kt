package com.hstan.autoservify.ui.main.Shops.SpareParts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hstan.autoservify.model.repositories.PartsCraftRepository
import kotlinx.coroutines.launch

class PartsCraftViewModel : ViewModel() {

    private val repository = PartsCraftRepository()

    private val _partsCrafts = MutableLiveData<List<PartsCraft>>()
    val partsCrafts: LiveData<List<PartsCraft>> = _partsCrafts

    fun loadPartsCrafts() {
        viewModelScope.launch {
            try {
                repository.getPartsCrafts().collect { partsCraftsList ->
                    _partsCrafts.value = partsCraftsList
                }
            } catch (e: Exception) {
                // Handle error
                _partsCrafts.value = emptyList()
            }
        }
    }
}
