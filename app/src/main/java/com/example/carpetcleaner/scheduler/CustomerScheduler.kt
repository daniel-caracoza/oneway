package com.example.carpetcleaner.scheduler

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.carpetcleaner.R
import com.example.carpetcleaner.data.FormCache
import com.example.carpetcleaner.databinding.FragmentCustomerSchedulerBinding
import java.time.LocalDate
import java.time.LocalTime

class CustomerScheduler : Fragment(), TimePickerFragment.TimePickerDialogListener, DatePickerFragment.DatePickerDialogListener {

    private lateinit var binding: FragmentCustomerSchedulerBinding
    private lateinit var viewModel: SchedulerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_customer_scheduler, container, false)
        viewModel = ViewModelProvider(this).get(SchedulerViewModel::class.java)
        binding.viewModel = viewModel
        binding.form = viewModel.form

        binding.date.editText?.inputType = InputType.TYPE_NULL
        binding.date.editText?.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus)
                showDatePickerDialog(v)
        }
        binding.date.editText?.setOnClickListener {
            showDatePickerDialog(it)
        }
        binding.time.editText?.inputType = InputType.TYPE_NULL
        binding.time.editText?.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus)
                showTimePickerDialog(v)
        }
        binding.time.editText?.setOnClickListener {
            showTimePickerDialog(it)
        }

        binding.time.editText?.doAfterTextChanged {
            viewModel.form.time = it.toString()
        }

        binding.customerNumber.editText?.doAfterTextChanged {
            val defaultCountry = "+1"
            val number = it.toString()
            val fullNumber = "$defaultCountry$number"
            viewModel.form.number = fullNumber
            viewModel.isNecessaryInformationProvided()
        }

        binding.customerName.editText?.doAfterTextChanged {
            viewModel.form.name = it.toString()
            viewModel.isNecessaryInformationProvided()
        }

        binding.customerAddress.editText?.doAfterTextChanged {
            viewModel.form.address = it.toString()
            viewModel.isNecessaryInformationProvided()
        }

        viewModel.oldDate.observe(viewLifecycleOwner, Observer { newDate ->
            viewModel.form.date = newDate.toString()
            binding.date.editText?.setText(newDate.toString())
        })

        //when all the required information is provided, the review button will enable
        viewModel.necessaryInformationProvided.observe(viewLifecycleOwner, Observer { toggleEnable ->
            binding.toReview.isEnabled = toggleEnable
        })

        binding.toReview.setOnClickListener {
            viewModel.cacheFormState()
            this.findNavController().navigate(
                CustomerSchedulerDirections.actionCustomerSchedulerToAppointmentConfirmation()
            )
        }
        return binding.root
    }


    private fun showTimePickerDialog(v: View){
        TimePickerFragment().show(this.childFragmentManager, "timePicker")
    }

    private fun showDatePickerDialog(v: View){
        DatePickerFragment().show(this.childFragmentManager, "datePicker")
    }

    override fun onDialogPositiveClick(hourOfDay: Int, minute: Int) {
        val t = LocalTime.of(hourOfDay, minute)
        if(viewModel.isValidTime(t)){
            binding.time.error = null
            binding.time.editText?.setText(t.toString())
            viewModel.isNecessaryInformationProvided()
        } else {
            binding.time.error = getString(R.string.error_time)
        }
    }

    override fun onDialogPositiveClick(year: Int, month: Int, dayOfMonth: Int) {
        val newDate = LocalDate.of(year, month, dayOfMonth)
        if(viewModel.isValidDate(newDate)){
            binding.date.error = null
            viewModel.updateDate(newDate)
            viewModel.isNecessaryInformationProvided()
        } else {
            binding.date.error = getString(R.string.invalid_date)
        }
    }

}