package com.rasenyer.contactsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rasenyer.contactsapp.R
import com.rasenyer.contactsapp.data.local.model.Contact
import com.rasenyer.contactsapp.databinding.FragmentHomeBinding
import com.rasenyer.contactsapp.ui.adapter.ContactAdapter
import com.rasenyer.contactsapp.ui.viewmodel.ContactViewModel
import com.rasenyer.contactsapp.ui.viewmodel.ContactViewModelFactory
import org.jetbrains.anko.doAsync

class HomeFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var contactViewModel: ContactViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val contactViewModelFactory = ContactViewModelFactory(requireActivity().application)
        contactViewModel = ViewModelProvider(this, contactViewModelFactory)[ContactViewModel::class.java]

        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
        binding.mRecyclerView.layoutManager = linearLayoutManager

        contactViewModel.getAllContacts.observe(viewLifecycleOwner, { userList ->
            binding.mRecyclerView.adapter = ContactAdapter(userList, :: deleteContact)
        })

        binding.mSearchView.setOnQueryTextListener(this)

        binding.mImageViewDeleteAll.setOnClickListener { deleteAllContacts() }

        binding.mFloatingActionButton.setOnClickListener { findNavController().navigate(R.id.action_HomeFragment_to_AddFragment) }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onQueryTextSubmit(name: String?): Boolean {

        if (name != null) { getContactsByName(name) }
        return true

    }

    override fun onQueryTextChange(name: String?): Boolean {

        if (name != null){ getContactsByName(name) }
        return true

    }

    private fun getContactsByName(name: String?){

        val nameQuery = "%$name%"

        contactViewModel.getContactsByName(nameQuery).observe(viewLifecycleOwner, { userList ->
            binding.mRecyclerView.adapter = ContactAdapter(userList, :: deleteContact)
        })

    }

    private fun deleteContact(contact: Contact) {
        doAsync { contactViewModel.deleteContact(contact) }
        Toast.makeText(context, R.string.deleted_contact, Toast.LENGTH_SHORT).show()
    }

    private fun deleteAllContacts(){

        activity?.let {
            AlertDialog.Builder(it).apply {
                setTitle(R.string.delete_all_contacts)
                setMessage(R.string.are_you_sure_you_want_to_delete_all_contacts)
                setCancelable(false)
                setPositiveButton(R.string.delete) { _, _ ->
                    doAsync { contactViewModel.deleteAllContacts() }
                    Toast.makeText(context, R.string.deleted_contacts, Toast.LENGTH_SHORT).show()
                }
                setNegativeButton(R.string.cancel, null)
            }.create().show()
        }

    }

}