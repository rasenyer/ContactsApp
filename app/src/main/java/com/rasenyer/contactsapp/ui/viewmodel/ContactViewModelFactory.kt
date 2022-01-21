package com.rasenyer.contactsapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ContactViewModelFactory(private var application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(ContactViewModel::class.java)) {
            return ContactViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }

}