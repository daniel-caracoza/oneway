package com.example.carpetcleaner.services

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ServicesViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ServicesViewModel::class.java)) {
            return ServicesViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}