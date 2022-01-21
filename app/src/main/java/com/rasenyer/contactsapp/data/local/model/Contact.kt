package com.rasenyer.contactsapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "contact_table")
data class Contact(

    @PrimaryKey(autoGenerate = true) val id: Int,
    val profilePicture: String?,
    val name: String?,
    val bio: String?,
    val countryCode: String?,
    val phoneNumber: Int?

): Serializable
