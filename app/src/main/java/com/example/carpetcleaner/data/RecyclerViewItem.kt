package com.example.carpetcleaner.data

import android.view.View

open class RecyclerViewItem {

    private var isExpanded = View.GONE

    fun setExpanded(bool: Boolean){
        isExpanded = if(bool) View.VISIBLE else View.GONE
    }

    fun isViewExpanded() : Boolean {
        return when (isExpanded) {
            View.VISIBLE -> true
            else -> false
        }
    }

    fun getExpanded():Int = isExpanded
}