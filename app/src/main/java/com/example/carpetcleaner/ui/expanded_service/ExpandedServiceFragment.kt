package com.example.carpetcleaner.ui.expanded_service

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.carpetcleaner.R
import com.example.carpetcleaner.data.FormCache
import com.example.carpetcleaner.data.ScheduledService
import com.example.carpetcleaner.data.ScheduledServiceCache
import com.example.carpetcleaner.databinding.FragmentExpandedServiceBinding
import com.example.carpetcleaner.ui.expanded_service.ExpandedServiceFragmentArgs
import com.example.carpetcleaner.ui.expanded_service.ExpandedServiceFragmentDirections
import com.example.carpetcleaner.themeColor
import com.google.android.material.transition.MaterialContainerTransform

class ExpandedServiceFragment : Fragment() {

    private val args: ExpandedServiceFragmentArgs by navArgs()
    private lateinit var binding: FragmentExpandedServiceBinding
    private lateinit var expandedServiceViewModelFactory: ExpandedServiceViewModelFactory
    private lateinit var expandedServiceViewModel: ExpandedServiceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.navHostFragment
            duration = resources.getInteger(R.integer.transition_motion_duration).toLong()
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(requireContext().themeColor(R.attr.colorSurface))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_expanded_service, container, false)
        expandedServiceViewModelFactory = ExpandedServiceViewModelFactory(args.serviceId)
        expandedServiceViewModel = ViewModelProvider(this, expandedServiceViewModelFactory).get(
            ExpandedServiceViewModel::class.java)
        binding.service = expandedServiceViewModel.service
        //initialize scheduledService
        val initScheduledService = ScheduledService(
            ScheduledServiceCache.getSize(),
            expandedServiceViewModel.service.id,
            expandedServiceViewModel.service.img,
            expandedServiceViewModel.service.title,
            expandedServiceViewModel.service.price,
            0
        )
        expandedServiceViewModel.scheduledService = initScheduledService
        //Dynamically add checboxes according to subservice list in service; none if list is null
        if (expandedServiceViewModel.subServices != null) {
            val textView = TextView(requireContext())
            textView.text = getString(R.string.additional_services)
            binding.linearLayout.addView(textView)

            expandedServiceViewModel.subServices?.forEachIndexed { index, subService ->
                val checkBox = CheckBox(requireActivity().applicationContext)
                val text = "${subService.title}: \$${subService.price}"
                checkBox.text = text
                //restore checkbox state
                checkBox.isChecked = expandedServiceViewModel.checkBoxSaveStates[index]
                checkBox.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        expandedServiceViewModel.addToTotal(subService.price)
                        expandedServiceViewModel.addToScheduleService(subService.title, subService.price)
                        expandedServiceViewModel.checkBoxSaveStates[index] = isChecked
                    } else {
                        expandedServiceViewModel.subtractFromTotal(subService.price)
                        expandedServiceViewModel.removeFromScheduleService(subService.title)
                        expandedServiceViewModel.checkBoxSaveStates[index] = false
                    }
                }
                binding.linearLayout.addView(checkBox, index + 1)
            }
        }

        //observer pattern to check for updates to total and if equal to zero to disable the navigation button
        expandedServiceViewModel.total.observe(viewLifecycleOwner, Observer { newTotal ->
            expandedServiceViewModel.isNavigationEnabled(newTotal)
            binding.totalPrice.text =  newTotal.toString()
        })
        //part of the observer pattern to check if navigation button should be enabled
        expandedServiceViewModel.isNavigateToSchedulerEnabled.observe(viewLifecycleOwner, Observer { isEnabled ->
            binding.navigateToScheduler.isEnabled = isEnabled
        })

        expandedServiceViewModel.navigateToSchedule.observe(viewLifecycleOwner, Observer { onNavigate ->
            if(onNavigate) {
                //if form.getForm is null, the user has not entered contact information; else navigate to @AppointmentConfirmation fragment
                if(FormCache.getForm() == null){
                    this.findNavController().navigate(
                        ExpandedServiceFragmentDirections.actionExpandedServiceFragmentToCustomerScheduler()
                    )
                    expandedServiceViewModel.onScheduleNavigated()
                } else this.findNavController().navigate(ExpandedServiceFragmentDirections.actionExpandedServiceFragmentToNavigation())
            }
        })

        binding.viewModel = expandedServiceViewModel

        return binding.root
    }

}