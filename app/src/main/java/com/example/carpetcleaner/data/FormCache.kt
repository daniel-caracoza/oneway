package com.example.carpetcleaner.data

object FormCache {
    private var cachedForm: Form? = null

    fun getForm(): Form? = cachedForm

    fun updateForm(newForm: Form) {
        cachedForm = newForm
    }
}

data class Form(
    var date: String,
    var time: String,
    var name: String,
    var number: String,
    var address: String,
    var propertyName: String?,
    var unit: String?
)