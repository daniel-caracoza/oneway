package com.example.carpetcleaner.review

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.carpetcleaner.R
import com.example.carpetcleaner.data.Form
import com.example.carpetcleaner.data.FormCache
import com.example.carpetcleaner.data.ScheduledService
import com.example.carpetcleaner.databinding.FragmentAppointmentConfirmationBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit


class AppointmentConfirmation : Fragment(), ScheduledServiceListener {

    private val TAG = "AppointmentConfirmation"
    private lateinit var binding: FragmentAppointmentConfirmationBinding
    private lateinit var viewModel: AppointmentConfirmationViewModel
    private val form: Form? = FormCache.getForm()
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        auth = Firebase.auth
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_appointment_confirmation, container, false)
        viewModel = ViewModelProvider(this).get(AppointmentConfirmationViewModel::class.java)
        binding.viewModel = viewModel
        if(form != null){
            binding.form = form
        }
        binding.editcontact.setOnClickListener {
            val directions = AppointmentConfirmationDirections.actionAppointmentConfirmationToCustomerScheduler()
            findNavController().navigate(directions)
        }

        val adapter = ScheduledServiceAdapter(this)
        binding.scheduleServiceList.adapter = adapter


        viewModel.scheduledServiceList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it.toList())
            }
        })
        //toggle the submit button dependent if the list is bigger than 0 (1 or more services scheduled) with viewModel method.
        viewModel.isSubmitEnabled.observe(viewLifecycleOwner, Observer {
            it?.let{
                requireActivity().invalidateOptionsMenu()
            }
        })

        viewModel.total.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.servicesTotal.text = getString(R.string.total_, it.toString())
            }
        })

        binding.addService.setOnClickListener {
            this.findNavController().navigate(AppointmentConfirmationDirections.actionAppointmentConfirmationToServicesFragment())
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.submit).isEnabled = viewModel.isSubmitEnabled.value!!
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.appbar_review, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when(item.itemId){
            R.id.submit -> {
                this.findNavController().navigate(
                    AppointmentConfirmationDirections.actionAppointmentConfirmationToSubmitFragment()
                )
                true
            } else -> true
        }

    override fun onDeleteClicked(scheduledService: ScheduledService) {
        viewModel.deleteScheduledService(scheduledService)
        viewModel.isSubmitEnabledAfterListChange()
        viewModel.updateTotal()
        Snackbar.make(
            binding.apptConfirmation,
            R.string.service_deleted,
            Snackbar.LENGTH_LONG
        ).show()
    }

    override fun onEditClicked(scheduledService: ScheduledService) {
        this.findNavController().navigate(
            AppointmentConfirmationDirections.actionAppointmentConfirmationToEditServiceFragment(
                scheduledService.serviceId, scheduledService.id
            )
        )
    }

    override fun onPopUpClicked(view: View, scheduledService: ScheduledService) {
        val popUp = PopupMenu(requireContext(), view)
        val inflater: MenuInflater = popUp.menuInflater
        inflater.inflate(R.menu.schedule_service_item_popup, popUp.menu)
        popUp.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.delete_scheduled_service -> {
                    onDeleteClicked(scheduledService)
                    true
                }
                R.id.edit_scheduled_service -> {
                    onEditClicked(scheduledService)
                    true
                }
                else -> true
            }
        }
        popUp.show()
    }

    override fun onClick(view: View, scheduledService: ScheduledService) {
        val expandableLayout = view.findViewById<ConstraintLayout>(R.id.expandableLayout)
        val isViewExpanded = scheduledService.isViewExpanded()
        val expander = view.findViewById<ImageView>(R.id.expander)
        if(!isViewExpanded) expander.animate().rotation(180f).duration = 300 else expander.animate().rotation(0f).duration = 300
        scheduledService.setExpanded(!isViewExpanded)
        expandableLayout.visibility = scheduledService.getExpanded()
    }
}