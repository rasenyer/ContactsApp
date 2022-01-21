package com.rasenyer.contactsapp.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rasenyer.contactsapp.data.local.dao.ContactDao
import com.rasenyer.contactsapp.data.local.model.Contact

@Database(entities = [Contact::class], version = 1)
abstract class ContactDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao

    companion object{

        private var INSTANCE: ContactDatabase? = null

        fun getInstance(context: Context): ContactDatabase{

            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context, ContactDatabase::class.java, "ContactDatabase").build()
            }

            return INSTANCE as ContactDatabase

        }

    }

}