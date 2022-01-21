package com.rasenyer.contactsapp.ui.fragments

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.github.drjacky.imagepicker.ImagePicker
import com.rasenyer.contactsapp.R
import com.rasenyer.contactsapp.data.local.model.Contact
import com.rasenyer.contactsapp.databinding.FragmentAddBinding
import com.rasenyer.contactsapp.ui.viewmodel.ContactViewModel
import com.rasenyer.contactsapp.ui.viewmodel.ContactViewModelFactory
import org.jetbrains.anko.doAsync

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private lateinit var contactViewModel: ContactViewModel
    private var imageUri: Uri? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val contactViewModelFactory = ContactViewModelFactory(requireActivity().application)
        contactViewModel = ViewModelProvider(this, contactViewModelFactory)[ContactViewModel::class.java]

        val countries = resources.getStringArray(R.array.countries)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.text_view, countries)
        binding.mAutoCompleteTextViewCountryCode.setAdapter(arrayAdapter)

        binding.mImageViewEdit.setOnClickListener {
            ImagePicker.with(context as Activity).cropSquare().createIntentFromDialog { launcherImage.launch(it) }
        }

        binding.mButtonSave.setOnClickListener { insertContact() }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val launcherImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

        if (it.resultCode == Activity.RESULT_OK) {
            imageUri = it.data?.data!!

            binding.mProfilePicture.load(imageUri){
                transformations(CircleCropTransformation())
                placeholder(R.drawable.ic_profile_picture)
                crossfade(true)
                crossfade(400)
            }

        }

    }

    private fun insertContact() {

        when {

            TextUtils.isEmpty(binding.mEditTextName.text.toString()) -> {
                binding.mEditTextName.error = resources.getString(R.string.enter_the_name)
            }
            TextUtils.isEmpty(binding.mEditTextBio.text.toString()) -> {
                binding.mEditTextBio.error = resources.getString(R.string.enter_bio)
            }
            TextUtils.isEmpty(binding.mAutoCompleteTextViewCountryCode.text.toString()) -> {
                Toast.makeText(context, R.string.choose_the_country_code, Toast.LENGTH_SHORT).show()
            }
            TextUtils.isEmpty(binding.mEditTextPhoneNumber.text.toString()) -> {
                binding.mEditTextPhoneNumber.error = resources.getString(R.string.enter_phone_number)
            }
            binding.mEditTextPhoneNumber.length() < 9 -> {
                binding.mEditTextPhoneNumber.error = resources.getString(R.string.the_phone_number_must_have_9_characters)
            }

            else -> {

                doAsync {

                    val contact = Contact(
                        id = 0,
                        profilePicture = imageUri.toString(),
                        name = binding.mEditTextName.text.toString(),
                        bio = binding.mEditTextBio.text.toString(),
                        countryCode = binding.mAutoCompleteTextViewCountryCode.text.toString(),
                        phoneNumber = Integer.parseInt(binding.mEditTextPhoneNumber.text.toString())
                    )

                    contactViewModel.insertContact(contact)

                }

                Toast.makeText(context, R.string.added_contact, Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_AddFragment_to_HomeFragment)

            }

        }

    }

}