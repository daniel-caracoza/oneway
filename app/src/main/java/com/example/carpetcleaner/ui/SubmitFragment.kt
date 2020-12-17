package com.example.carpetcleaner.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.carpetcleaner.R
import com.example.carpetcleaner.ui.SubmitFragmentDirections
import com.example.carpetcleaner.data.FormCache
import com.example.carpetcleaner.data.ScheduledServiceCache
import com.example.carpetcleaner.data.User
import com.example.carpetcleaner.databinding.FragmentSubmitBinding
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class SubmitFragment : Fragment() {

    private val TAG = "SubmitFragment"
    private val form = FormCache.getForm()
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentSubmitBinding
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var db: FirebaseFirestore
    private val scheduledServices = ScheduledServiceCache.getScheduledServices()
    private val DELAY: Long = 60000

    private val callbacks = object: PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            Log.d(TAG, "onVerificationCompleted:$credential")
        }
        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w(TAG, "onVerificationFailed", e)
            if (e is FirebaseAuthInvalidCredentialsException) {
                Log.e(TAG, "Invalid request")

            } else if (e is FirebaseTooManyRequestsException) {
                Log.e(TAG, "Too many requests")
            }
        }

        override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.d(TAG, "onCodeSent:$verificationId")
            // Save verification ID and resending token so we can use them later
            storedVerificationId = verificationId
            resendToken = token
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Firebase.firestore
        auth = Firebase.auth
        verify()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_submit, container, false)
        binding.enter.setOnClickListener {
            val code = binding.verificationCode.editText?.text.toString()
            val credential = PhoneAuthProvider.getCredential(storedVerificationId!!, code)
            signInWithPhoneAuthCredential(credential)
        }
        binding.resend.setOnClickListener {
            resendOTP()
        }
        //enable resend button after 60 seconds has passed
        Handler(Looper.getMainLooper())
            .postDelayed({
                binding.resend.isEnabled = true
            }, DELAY)
        return binding.root
    }

    private fun verify(){
        val phoneNumber = "+1${form!!.number}"
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun resendOTP(){
        val phoneNumber = "+1${form!!.number}"
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(callbacks)
            .setForceResendingToken(resendToken)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    createDBEntry()
                    this.findNavController().navigate(
                        SubmitFragmentDirections.actionSubmitFragmentToAppointmentRequestReceivedFragment()
                    )
                    val user = task.result?.user
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        binding.verificationCode.error = getString(R.string.invalid_verification)
                    }
                }
            }
    }

    //add scheduled services list to firestore database
    private fun createDBEntry(){
        val user = User(form!!.name, form.number, form.address)

        //document for the date selected in scheduling
        val schedule = db.collection("schedule").document(form.date)

        //document for appointment
        val appointment  = schedule.collection("appointments").document()

        appointment.set(user)

        val services = appointment.collection("services")

        scheduledServices.value!!.forEach {
            val serviceDoc = services.document()
            val data = hashMapOf<String, Any>(
                "mainService" to it.mainService,
                "basePrice" to it.mainPrice,
                "total" to it.total
            )
            if (it.isSubServicesNotEmpty()){
                it.subServices.forEachIndexed{ index, sub ->
                    val subData = hashMapOf(
                        "title" to sub.title,
                        "price" to sub.price
                    )
                    data["subService$index"] = subData
                }
            }
            serviceDoc.set(data)
        }

    }
}