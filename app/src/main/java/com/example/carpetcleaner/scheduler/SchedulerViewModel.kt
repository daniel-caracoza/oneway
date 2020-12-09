package com.example.carpetcleaner.scheduler

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carpetcleaner.data.Form
import com.example.carpetcleaner.data.FormCache
import java.time.LocalDate
import java.time.LocalTime

class SchedulerViewModel: ViewModel() {

    var date: String = ""
    var time: String = ""
    var name: String = ""
    var number: String = ""
    var address: String = ""
    var propertyName:String = ""
    var unit = ""

    val form = FormCache.getForm() ?: Form(
        date, time, name, number, address, propertyName, unit
    )


    private val _oldDate = MutableLiveData<LocalDate>()

    val oldDate: LiveData<LocalDate>
        get() = _oldDate

    private val _necessaryInformationProvided = MutableLiveData<Boolean>()

    val necessaryInformationProvided: LiveData<Boolean>
        get() = _necessaryInformationProvided


    init {
        _necessaryInformationProvided.value = false
    }

    fun isValidDate(newDate: LocalDate) = newDate.isAfter(LocalDate.now())

    fun isValidTime(time: LocalTime): Boolean {
        val min = LocalTime.of(8,0)
        val max = LocalTime.of(16,0)
        return time.isAfter(min)  && time.isBefore(max)
    }

    fun updateDate(newDate: LocalDate){
        _oldDate.value = newDate
    }

    //turn on the button once the all the required fields are filled
    fun isNecessaryInformationProvided() {
        _necessaryInformationProvided.value =_oldDate.value != null && form.name.isNotBlank() && form.address.isNotBlank() &&
                form.number.isNotBlank() && form.time.isNotBlank()
    }

    fun cacheFormState() {
        FormCache.updateForm(form)
    }




}