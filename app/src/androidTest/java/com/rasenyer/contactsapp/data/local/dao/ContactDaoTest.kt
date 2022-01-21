package com.rasenyer.contactsapp.data.local.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.rasenyer.contactsapp.data.local.database.ContactDatabase
import com.rasenyer.contactsapp.data.local.model.Contact
import com.rasenyer.contactsapp.getOrAwaitValue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ContactDaoTest {

    private lateinit var contactDao: ContactDao
    private lateinit var contactDatabase: ContactDatabase

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDatabase() {

        val context = ApplicationProvider.getApplicationContext<Context>()
        contactDatabase = Room.inMemoryDatabaseBuilder(context, ContactDatabase::class.java).build()
        contactDao = contactDatabase.contactDao()

    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {

        contactDatabase.close()

    }

    @Test
    @Throws(Exception::class)
    fun insertContact() {

        val contact = Contact(id = 1, profilePicture = "", name = "Jorge", bio = "Android Developer", countryCode = "ECU +593", phoneNumber = 123456789)
        contactDao.insertContact(contact)

        val getAllContacts = contactDao.getAllContacts().getOrAwaitValue()
        assertThat(getAllContacts).contains(contact)

    }

    @Test
    @Throws(Exception::class)
    fun updateContact() {

        val contact = Contact(id = 1, profilePicture = "", name = "Jorge", bio = "Android Developer", countryCode = "ECU +593", phoneNumber = 123456789)
        contactDao.insertContact(contact)

        val contactUpdated = Contact(id = 1, profilePicture = "", name = "Jorge", bio = "Android Developer", countryCode = "ECU +593", phoneNumber = 987654321)
        contactDao.updateContact(contactUpdated)

        assertThat(contact).isNotEqualTo(contactUpdated)

    }

    @Test
    @Throws(Exception::class)
    fun deleteContact() {

        val contact = Contact(id = 1, profilePicture = "", name = "Jorge", bio = "Android Developer", countryCode = "ECU +593", phoneNumber = 123456789)
        contactDao.insertContact(contact)
        contactDao.deleteContact(contact)

        val getAllContacts = contactDao.getAllContacts().getOrAwaitValue()
        assertThat(getAllContacts).doesNotContain(contact)

    }

    @Test
    @Throws(Exception::class)
    fun deleteAllContacts() {

        val contact1 = Contact(id = 1, profilePicture = "", name = "Jorge", bio = "Android Developer", countryCode = "ECU +593", phoneNumber = 123456789)
        contactDao.insertContact(contact1)

        val contact2 = Contact(id = 2, profilePicture = "", name = "Darwin", bio = "Teacher", countryCode = "ECU +593", phoneNumber = 123456789)
        contactDao.insertContact(contact2)

        val contact3 = Contact(id = 3, profilePicture = "", name = "Fabian", bio = "Military", countryCode = "ECU +593", phoneNumber = 123456789)
        contactDao.insertContact(contact3)

        contactDao.deleteAllContacts()

        val getAllContacts = contactDao.getAllContacts().getOrAwaitValue()
        assertThat(getAllContacts).isEmpty()

    }

    @Test
    @Throws(Exception::class)
    fun getContactsByName() {

        val contact1 = Contact(id = 1, profilePicture = "", name = "Jorge", bio = "Android Developer", countryCode = "ECU +593", phoneNumber = 123456789)
        contactDao.insertContact(contact1)

        val contact2 = Contact(id = 2, profilePicture = "", name = "Darwin", bio = "Teacher", countryCode = "ECU +593", phoneNumber = 123456789)
        contactDao.insertContact(contact2)

        val contact3 = Contact(id = 3, profilePicture = "", name = "Fabian", bio = "Military", countryCode = "ECU +593", phoneNumber = 123456789)
        contactDao.insertContact(contact3)

        val getContactsByName = contactDao.getContactsByName(name = "Jorge").getOrAwaitValue()
        assertThat(getContactsByName).isNotEmpty()

    }

    @Test
    @Throws(Exception::class)
    fun getAllContacts() {

        val contact1 = Contact(id = 1, profilePicture = "", name = "Jorge", bio = "Android Developer", countryCode = "ECU +593", phoneNumber = 123456789)
        contactDao.insertContact(contact1)

        val contact2 = Contact(id = 2, profilePicture = "", name = "Darwin", bio = "Teacher", countryCode = "ECU +593", phoneNumber = 123456789)
        contactDao.insertContact(contact2)

        val contact3 = Contact(id = 3, profilePicture = "", name = "Fabian", bio = "Military", countryCode = "ECU +593", phoneNumber = 123456789)
        contactDao.insertContact(contact3)

        val getAllContacts = contactDao.getAllContacts().getOrAwaitValue()
        assertThat(getAllContacts).isNotEmpty()

    }

}