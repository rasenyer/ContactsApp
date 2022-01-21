package com.rasenyer.contactsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.rasenyer.contactsapp.R
import com.rasenyer.contactsapp.data.local.model.Contact
import com.rasenyer.contactsapp.databinding.ItemContactBinding
import com.rasenyer.contactsapp.ui.fragments.HomeFragmentDirections

class ContactAdapter(private val contactList: List<Contact>, private val onDeleteContactCallback: (Contact) -> Unit): RecyclerView.Adapter<ContactAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemContactBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, position: Int) {

        val contact = contactList[position]

        myViewHolder.binding.mProfilePicture.load(contact.profilePicture){
            transformations(CircleCropTransformation())
            placeholder(R.drawable.ic_profile_picture)
            error(R.drawable.ic_profile_picture)
            crossfade(true)
            crossfade(400)
        }
        myViewHolder.binding.mTextViewName.text = contact.name
        myViewHolder.binding.mTextViewBio.text = contact.bio
        myViewHolder.binding.mTextViewCountryCode.text = contact.countryCode
        myViewHolder.binding.mTextViewPhoneNumber.text = contact.phoneNumber.toString()

        myViewHolder.binding.mImageViewEdit.setOnClickListener {
            val direction = HomeFragmentDirections.actionHomeFragmentToUpdateFragment(contact)
            it.findNavController().navigate(direction)
        }

        myViewHolder.binding.mImageViewDelete.setOnClickListener { onDeleteContactCallback(contact) }

    }

    override fun getItemCount(): Int { return contactList.size }

    class MyViewHolder(val binding : ItemContactBinding): RecyclerView.ViewHolder(binding.root)

}