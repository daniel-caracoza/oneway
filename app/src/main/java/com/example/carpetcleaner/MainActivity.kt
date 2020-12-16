package com.example.carpetcleaner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.AWSDataStorePlugin

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = this.findNavController(R.id.navHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController)

//        try {
//            Amplify.addPlugin(AWSApiPlugin())
//            Amplify.addPlugin(AWSDataStorePlugin())
//            Amplify.configure(applicationContext)
//            Log.i("Amplify", "Initialized Amplify")
//        } catch (e: AmplifyException) {
//            Log.e("Amplify", "Could not initialize Amplify", e)
//        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.navHostFragment)
        return navController.navigateUp()
    }
}