package com.example.carpetcleaner.review

import android.os.Bundle
import android.view.*
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.carpetcleaner.R
import com.example.carpetcleaner.data.ScheduledServiceCache
import com.example.carpetcleaner.databinding.FragmentEditServiceBinding
import com.example.carpetcleaner.expanded_service.ExpandedServiceViewModel
import com.example.carpetcleaner.expanded_service.ExpandedServiceViewModelFactory
import com.google.android.material.snackbar.Snackbar

class EditServiceFragment : Fragment() {
    private lateinit var binding: FragmentEditServiceBinding
    private lateinit var viewModel: ExpandedServiceViewModel
    private lateinit var viewModelFactory: ExpandedServiceViewModelFactory
    private val args: EditServiceFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_service, container,false)
        viewModelFactory = ExpandedServiceViewModelFactory(args.serviceId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ExpandedServiceViewModel::class.java)
        binding.service = viewModel.service
        val scheduledService = ScheduledServiceCache.getScheduledServiceAt(args.scheduledServiceId)
        viewModel.scheduledService = scheduledService
        viewModel.checkBoxSaveStates = scheduledService.checkBoxStates
        viewModel.setTotal(scheduledService.total)

        //Dynamically add checboxes according to subservice list in service; none if list is null
        if (viewModel.subServices != null) {
            val textView = TextView(requireContext())
            textView.text = getString(R.string.additional_services)
            binding.linearLayout.addView(textView)

            viewModel.subServices?.forEachIndexed { index, subService ->
                val checkBox = CheckBox(requireActivity().applicationContext)
                val text = "${subService.title}: ${subService.price}"
                checkBox.text = text
                //restore checkbox state
                checkBox.isChecked = viewModel.checkBoxSaveStates[index]
                checkBox.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        viewModel.addToTotal(subService.price)
                        viewModel.addToScheduleService(subService.title, subService.price)
                        viewModel.checkBoxSaveStates[index] = isChecked
                    } else {
                        viewModel.subtractFromTotal(subService.price)
                        viewModel.removeFromScheduleService(subService.title)
                        viewModel.checkBoxSaveStates[index] = false
                    }
                }
                binding.linearLayout.addView(checkBox, index + 1)
            }
        }
        viewModel.total.observe(viewLifecycleOwner, Observer {
            binding.totalPrice.text = "$it"
        })
        binding.viewModel = viewModel
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.appbar2, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.done -> {
                if(viewModel.isTotalBiggerThanZero()) {
                    viewModel.onServiceEdited()
                    this.findNavController().navigate(
                        EditServiceFragmentDirections.actionEditServiceFragmentToAppointmentConfirmation()
                    )
                } else {
                    Snackbar.make(binding.editServiceConstraintLayout,
                        R.string.no_service_selected, Snackbar.LENGTH_LONG).show()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



}