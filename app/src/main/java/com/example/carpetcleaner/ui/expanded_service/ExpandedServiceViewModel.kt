package com.example.carpetcleaner.ui.expanded_service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carpetcleaner.data.*

class ExpandedServiceViewModel(serviceid: Long): ViewModel(){

    val service: Service = ServiceStore.getServiceById(serviceid)

    val subServices = service.subservices

    //save the states of the dynamic checkboxes
    var checkBoxSaveStates = mutableListOf<Boolean>()

    private val _total = MutableLiveData<Int>()

    val total: LiveData<Int>
        get() = _total

    var scheduledService: ScheduledService

    private val _isNavigateToSchedulerEnabled = MutableLiveData<Boolean>()

    val isNavigateToSchedulerEnabled: LiveData<Boolean>
        get() = _isNavigateToSchedulerEnabled

    private val _navigateToSchedule = MutableLiveData<Boolean>()

    val navigateToSchedule: LiveData<Boolean>
        get() = _navigateToSchedule

    //set the initial price
    init {
        scheduledService = ScheduledService(
            ScheduledServiceCache.getSize(),
            service.id,
            service.img,
            service.title,
            service.price,
            0
        )
        _total.value = service.price
        initCheckBoxStates()
    }

    private fun initCheckBoxStates() {
        subServices?.forEachIndexed { index, _ ->
            checkBoxSaveStates.add(index, false)
        }
    }

    fun addToTotal(addedPrice: Int){
        _total.value = _total.value?.plus(addedPrice)
    }

    fun subtractFromTotal(subPrice: Int){
        _total.value = _total.value?.minus(subPrice)
    }

    //enabled if total above 0
    fun isNavigationEnabled(newTotal: Int) {
         when(newTotal){
            0 -> _isNavigateToSchedulerEnabled.value = false
            else -> _isNavigateToSchedulerEnabled.value = true
        }
    }

    fun isTotalBiggerThanZero(): Boolean = _total.value!! > 0

    //add subservice (clicking the ui checkboxes)
    fun addToScheduleService(addedTitle: String, addedPrice: Int){
        val subService = SubService(
            addedTitle,
            addedPrice
        )
        scheduledService.subServices.add(subService)
    }

    fun removeFromScheduleService(title: String){
        val scheduledSubServices = scheduledService.subServices
        val findSubService = scheduledSubServices.find { it.title == title }
        if(findSubService != null){
            scheduledSubServices.remove(findSubService)
        }
    }

    fun onNavigateToScheduler(){
        scheduledService.total = total.value!!
        scheduledService.checkBoxStates = checkBoxSaveStates
        ScheduledServiceCache.addToScheduledServices(scheduledService)
        _navigateToSchedule.value = true
    }

    fun onScheduleNavigated(){
        _navigateToSchedule.value = false
        _total.value = service.price
        //initCheckBoxStates()
    }

    //review/customer scheduler use to replace the old scheduled service to update after edited
    fun onServiceEdited(){
        scheduledService.total = _total.value!!
        scheduledService.checkBoxStates = checkBoxSaveStates
        ScheduledServiceCache.updateScheduledServiceAt(
                scheduledService.id,
                scheduledService
            )
    }

    fun setTotal(newTotal: Int){
        _total.value = newTotal
    }

}