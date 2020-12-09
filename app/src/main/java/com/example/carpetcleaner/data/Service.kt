package com.example.carpetcleaner.data

import androidx.annotation.DrawableRes

data class Service(
    val id: Long,
    val title: String,
    val subtitle: String,
    val price: Int,
    val subservices: List<SubService>?,
    @DrawableRes
    val img: Int
)

data class SubService(
    val title: String,
    val price: Int
)