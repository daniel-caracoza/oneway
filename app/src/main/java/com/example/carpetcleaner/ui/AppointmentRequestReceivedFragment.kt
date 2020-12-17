package com.example.carpetcleaner.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.carpetcleaner.ui.AppointmentRequestReceivedFragmentDirections
import com.example.carpetcleaner.R
import com.example.carpetcleaner.data.FormCache

class AppointmentRequestReceivedFragment : Fragment() {

    private val form = FormCache.getForm()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_appointment_request_received, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.txtView).text = getString(R.string.closing_msg, form!!.name, form.date)

        view.findViewById<Button>(R.id.goHome).setOnClickListener {
            this.findNavController().navigate(
                AppointmentRequestReceivedFragmentDirections.actionAppointmentRequestReceivedFragmentToLaunchFragment()
            )
        }
    }
}