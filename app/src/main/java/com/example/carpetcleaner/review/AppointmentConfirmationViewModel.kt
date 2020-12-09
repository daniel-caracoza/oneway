package com.example.carpetcleaner.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carpetcleaner.data.ScheduledService
import com.example.carpetcleaner.data.ScheduledServiceCache

class AppointmentConfirmationViewModel(): ViewModel() {


    val scheduledServiceList = ScheduledServiceCache.getScheduledServices()

    private val _total = MutableLiveData<Int>()

    val total: LiveData<Int>
        get() = _total

    private val _isSubmitEnabled = MutableLiveData<Boolean>()

    val isSubmitEnabled: LiveData<Boolean>
        get() = _isSubmitEnabled

    init {
        isSubmitEnabledAfterListChange()
        _total.value = ScheduledServiceCache.getTotal()
    }

    fun deleteScheduledService(scheduledService: ScheduledService){
        scheduledServiceList.remove(scheduledService)
    }

    fun isSubmitEnabledAfterListChange(){
        when (scheduledServiceList.value!!.size) {
            0 -> {
                _isSubmitEnabled.value = false
            }
            else -> {
                _isSubmitEnabled.value = true
            }
        }
    }

    fun updateTotal(){
        _total.value = ScheduledServiceCache.getTotal()
    }
}

fun <T> MutableLiveData<MutableList<T>>.removeAt(index: Int){
    if(!this.value.isNullOrEmpty()){
        val oldValue = this.value
        oldValue?.removeAt(index)
        this.value = oldValue
    } else this.value = mutableListOf()
}

fun <T> MutableLiveData<MutableList<T>>.remove(obj: T){
    if(!this.value.isNullOrEmpty()){
        val oldValue = this.value
        oldValue?.remove(obj)
        this.value = oldValue
    } else this.value = mutableListOf()
}