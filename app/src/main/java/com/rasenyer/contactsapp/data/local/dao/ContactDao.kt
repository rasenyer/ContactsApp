package com.rasenyer.contactsapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rasenyer.contactsapp.data.local.model.Contact

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContact(contact: Contact)

    @Update
    fun updateContact(contact: Contact)

    @Delete
    fun deleteContact(contact: Contact)

    @Query("DELETE FROM contact_table")
    fun deleteAllContacts()

    @Query("SELECT * FROM contact_table WHERE name LIKE :name")
    fun getContactsByName(name: String?): LiveData<List<Contact>>

    @Query("SELECT * FROM contact_table ORDER BY id DESC")
    fun getAllContacts(): LiveData<List<Contact>>

}