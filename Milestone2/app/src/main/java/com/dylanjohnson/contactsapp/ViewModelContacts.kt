package com.dylanjohnson.contactsapp

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.dylanjohnson.contactsapp.models.ContactModel

class ViewModelContacts (context: Context, file_name: String): ViewModel() {
    // Private mutable lists
    private var _contactsList = mutableStateListOf<ContactModel>()
    private var _contactsSearchResults = mutableStateListOf<ContactModel>()

    // Public Lists
    val contactsList: List<ContactModel> get() = _contactsList
    val contactsSearchResults: List<ContactModel> get() = _contactsSearchResults

    // Adding a contact
    fun addContact(newContact: ContactModel) {
        _contactsList.add(newContact)
    }

    // Deleting a contact
    fun removeContact(contactID: Int): Int? {
        for (contact: ContactModel in _contactsList) {
            if (contact.getId() == contactID) {
                println("Removing ${contact.getName()} with $contactID from contact list")
                _contactsList.remove(contact)
                return contactID
            }
        }
        return null
    }

    // Get a contact
    fun getContact(contactID: Int): ContactModel? {
        for (contact: ContactModel in _contactsList) {
            if (contact.getId() == contactID) {
                println(contact)
                return contact
            }
        }
        return null
    }

    // Search for a contact
    fun searchForContactName(contactName: String): MutableList<ContactModel> {
        _contactsSearchResults.clear()
        _contactsSearchResults.addAll(_contactsList.filter { it.getName().contains(contactName, ignoreCase = true) })
        return _contactsSearchResults
    }

    // Update a contact
    fun updateContact(newContact: ContactModel): ContactModel? {
        for (i in _contactsList.indices) {
            if (_contactsList[i].getId() == newContact.getId()) {
                println("Updating $_contactsList[i] into $newContact")
                _contactsList[i] = newContact
                return newContact
            }
        }
        return null
    }

    // Save contacts to a JSON file
    fun saveContacts() {
        // TODO: Add code to save contacts into a JSON format
    }

    // Load contacts from a JSON file
    fun loadContacts() {
        // TODO: Add code to load contacts from a JSON format
    }

}