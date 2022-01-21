package com.rasenyer.contactsapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rasenyer.contactsapp.data.local.database.ContactDatabase
import com.rasenyer.contactsapp.data.local.model.Contact

class ContactViewModel(application: Application) : ViewModel() {

    private val contactDatabase: ContactDatabase = ContactDatabase.getInstance(application)

    fun insertContact(contact: Contact){
        contactDatabase.contactDao().insertContact(contact)
    }

    fun updateContact(contact: Contact) {
        contactDatabase.contactDao().updateContact(contact)
    }

    fun deleteContact(contact: Contact) {
        contactDatabase.contactDao().deleteContact(contact)
    }

    fun deleteAllContacts() {
        contactDatabase.contactDao().deleteAllContacts()
    }

    fun getContactsByName(name: String?): LiveData<List<Contact>> {
        return contactDatabase.contactDao().getContactsByName(name)
    }

    internal val getAllContacts : LiveData<List<Contact>> = contactDatabase.contactDao().getAllContacts()

}