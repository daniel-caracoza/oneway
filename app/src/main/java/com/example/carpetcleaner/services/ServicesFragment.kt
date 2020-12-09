package com.example.carpetcleaner.services

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.carpetcleaner.R
import com.example.carpetcleaner.data.Service
import com.example.carpetcleaner.databinding.FragmentServicesBinding
import com.google.android.material.transition.MaterialElevationScale


class ServicesFragment : Fragment(), ServiceListener {

    private lateinit var binding: FragmentServicesBinding
    private lateinit var servicesViewModel: ServicesViewModel
    private lateinit var servicesViewModelFactory: ServicesViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_services, container, false)
        servicesViewModelFactory = ServicesViewModelFactory()
        servicesViewModel = ViewModelProvider(this, servicesViewModelFactory).get(ServicesViewModel::class.java)

        val layoutManager = GridLayoutManager(requireContext(), 2)
        val adapter = ServicesAdapter(this)
        binding.servicesList.layoutManager = layoutManager
        binding.servicesList.adapter = adapter

        servicesViewModel.services.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
        servicesViewModel.navigateToExpandedService.observe(viewLifecycleOwner, Observer { serviceId ->
            serviceId?.let {
                this.findNavController().navigate(
                    ServicesFragmentDirections
                        .actionServicesFragmentToExpandedServiceFragment(serviceId))
                servicesViewModel.onExpandedServiceNavigated()
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
    }

    override fun onClick(cardView: View, service: Service) {
        exitTransition = MaterialElevationScale(false).apply {
            duration = resources.getInteger(R.integer.transition_motion_duration).toLong()
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = resources.getInteger(R.integer.transition_motion_duration).toLong()
        }
        val expandedServiceTransitionName = getString(R.string.expanded_service_transition_name)
        val extras = FragmentNavigatorExtras(cardView to expandedServiceTransitionName)
        val directions = ServicesFragmentDirections.actionServicesFragmentToExpandedServiceFragment(service.id)
        findNavController().navigate(directions, extras)
    }

}