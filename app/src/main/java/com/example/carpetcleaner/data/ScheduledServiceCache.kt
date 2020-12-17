package com.example.carpetcleaner.data

import androidx.annotation.DrawableRes
import androidx.lifecycle.MutableLiveData

object ScheduledServiceCache {

    private val scheduledServices = MutableLiveData<MutableList<ScheduledService>>()

    fun getScheduledServices() = scheduledServices

    fun addToScheduledServices(scheduledService: ScheduledService){
        scheduledServices.value!!.add(scheduledService)
    }

    fun getSize() = scheduledServices.value!!.size

    fun getScheduledServiceAt(index: Int) = scheduledServices.value!![index]

    fun updateScheduledServiceAt(index: Int, scheduledService: ScheduledService){
        scheduledServices.value!![index] = scheduledService
    }

    fun getTotal(): Int {
        return if(scheduledServices.value!!.isEmpty()){
            0
        } else {
            var total = 0
            scheduledServices.value!!.forEach {
                total += it.total
            }
            total
        }
    }

    init {
        scheduledServices.value = mutableListOf()
    }
}

data class ScheduledService(
    val id: Int,
    val serviceId: Long,
    @DrawableRes
    val serviceImg: Int,
    val mainService: String,
    val mainPrice: Int,
    var total: Int,
    val subServices: MutableList<SubService> = mutableListOf(),
    var checkBoxStates: MutableList<Boolean> = mutableListOf()
): RecyclerViewItem() {

    override fun equals(other: Any?): Boolean {
        other as ScheduledService
        return this.id == other.id && this.serviceId == other.serviceId && this.total == other.total &&
                compareSubServices(other.subServices) && this.checkBoxStates == other.checkBoxStates
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + serviceId.hashCode()
        result = 31 * result + mainService.hashCode()
        result = 31 * result + mainPrice
        result = 31 * result + total
        result = 31 * result + subServices.hashCode()
        result = 31 * result + checkBoxStates.hashCode()
        return result
    }

    private fun compareSubServices(other: MutableList<SubService>): Boolean {
        val set1 = this.subServices.toSet()
        val set2 = other.toSet()
        return set1 == set2
    }

    fun isSubServicesNotEmpty(): Boolean = subServices.isNotEmpty()
}

