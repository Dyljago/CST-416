package com.dylanjohnson.contactsapp.data

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import java.io.File
import com.dylanjohnson.contactsapp.models.ContactModel
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelJSON(context: Context) : JokesViewModelInterface, ViewModel() {
    val appContext = context
    override var contactsList: MutableList<ContactModel> = mutableStateListOf<ContactModel>()
    override var contactsSearchResult: MutableList<ContactModel> = mutableStateListOf<ContactModel>()

    fun loadContactsFromJSON() {
        Log.d("ViewModelJSON", "Loading Contacts from JSON")
        val file = File(appContext.filesDir, "contacts.json")

        val mapper = ObjectMapper()
        if (file.exists()) {
            // read the entire list of jokes from the json file.
            val newContacts = mapper.readValue(file, Array<ContactModel>::class.java).toMutableList()
            // remove all jokes from the list and add the new jokes
            contactsList.clear()
            contactsList.addAll(newContacts)
            /*
                Why not do it this way?
                jokesList = mapper.readValue(file, Array<JokeModel>::class.java).toMutableList()
                According to the way mutableStateListOf works, we cannot simply assign a new list to jokesList
                The UI will not be updated. We must replace the items in the list. This triggers the recomposition of the list
            */
            Log.d("ViewModelJSON", "Contacts loaded from JSON: ${contactsList.size}")
        }
    }

    fun saveContactsToJSON() {
        // perform the file IO on the IO thread using coroutine to prevent blocking the main thread
        // blocking the main thread can cause the app to freeze and become unresponsive during file IO
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("ViewModelJSON", "Saving contacts to JSON")
            val file = File(appContext.filesDir, "contacts.json")
            val mapper = ObjectMapper()
            mapper.writeValue(file, contactsList)
        }
        Log.d("ViewModelJSON", "Contacts saved to JSON: ${contactsList.size}")
    }

    override fun saveContacts() {
        saveContactsToJSON()
    }

    override fun findContactsByKeyword(findPhrase: String) {
        // clear previous search results
        contactsSearchResult.clear()

        // search the jokesList for the findPhrase
        contactsList.forEach {
            if (it.getName().contains(findPhrase) || it.getPhoneNum().contains(findPhrase)) {
                contactsSearchResult.add(it)
            }
        }
    }

    override fun getAllContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("ViewModelJSON", "Loading Contacts from JSON")
            val file = File(appContext.filesDir, "contacts.json")

            val mapper = ObjectMapper()
            if (file.exists()) {
                val newJokes = mapper.readValue(file, Array<ContactModel>::class.java).toMutableList()
                // Update UI-related data on the main thread
                launch(Dispatchers.Main) {
                    contactsList.clear()
                    contactsList.addAll(newJokes)
                    Log.d("ViewModelJSON", "Contacts loaded from JSON: ${contactsList.size}")
                }
            }
        }
    }

    override fun addContact(contact: ContactModel) {
        // add the joke to the list and save the list to the JSON file
        contactsList.add(contact)
        saveContactsToJSON()
    }

    override fun removeContact(contact: ContactModel) {
        // remove the joke from the list and save the list to the JSON file
        contactsList.remove(contact)
        saveContactsToJSON()
    }

    override fun hideShowContact(contact: ContactModel) {
        // determine which list to use
        var contactsList = if (contactsSearchResult.isEmpty()) contactsList else contactsSearchResult
        // set all visible to false
        contactsList.forEach{ it.setExpanded(false) }
        // set the joke passed in to true
        contactsList[contactsList.indexOf(contact)] = contact.copy(isExpanded = true)
    }

}