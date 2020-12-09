package com.example.carpetcleaner.services

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carpetcleaner.data.ServiceStore

class ServicesViewModel: ViewModel(){

    val services = ServiceStore.getAllServices()

    private val _navigateToExpandedService = MutableLiveData<Long>()

    val navigateToExpandedService: LiveData<Long>
        get() = _navigateToExpandedService

    fun onServiceClicked(id: Long){
        _navigateToExpandedService.value = id
    }

    fun onExpandedServiceNavigated() {
        _navigateToExpandedService.value = null
    }

}