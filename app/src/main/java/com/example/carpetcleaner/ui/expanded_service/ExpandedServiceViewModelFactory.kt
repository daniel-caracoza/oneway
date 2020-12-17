package com.example.carpetcleaner.ui.expanded_service

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ExpandedServiceViewModelFactory(private val serviceid: Long): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ExpandedServiceViewModel::class.java))
            return ExpandedServiceViewModel(serviceid) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}