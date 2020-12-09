package com.example.carpetcleaner.scheduler

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment: DialogFragment(), DatePickerDialog.OnDateSetListener {
    
    private lateinit var listener: DatePickerDialogListener

    interface DatePickerDialogListener {
        fun onDialogPositiveClick(year: Int, month: Int, dayOfMonth: Int)
    }
    
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        listener = parentFragment as DatePickerDialogListener
        //use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH) + 1

        return DatePickerDialog(requireActivity(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        listener.onDialogPositiveClick(year, month + 1, dayOfMonth)
    }

}