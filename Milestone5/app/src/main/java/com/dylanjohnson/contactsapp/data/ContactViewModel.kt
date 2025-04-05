package com.dylanjohnson.contactsapp.data

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dylanjohnson.contactsapp.models.BusinessModel
import com.dylanjohnson.contactsapp.models.ContactModel
import com.dylanjohnson.contactsapp.models.PersonModel
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.time.LocalDate
import com.fasterxml.jackson.databind.type.TypeFactory
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.SerializationFeature

class ContactViewModel (context: Context) : ContactViewModelInterface, ViewModel() {
    val appContext = context
    override var contactsList = mutableStateListOf<ContactModel>()
    override var contactsSearchResult = mutableStateListOf<ContactModel>()

    init {
        // Set contacts
        contactsList.addAll( listOf(
                PersonModel(
                    id = 0,
                    name = "Person Example",
                    email = "test@test.com",
                    phoneNumber = "555-555-5555",
                    address = "11111 east fake street, Zoo, Narnia 99999",
                    isExpanded = false,
                    pictureURL = null,
                    isFamilyMember = true,
                    birthDate = LocalDate.now(),
                    group = "",
                    isEmergency = false,
                    isFavorite = true
                ),
                BusinessModel(
                    id = 1,
                    name = "Business Example",
                    email = "business@test.com",
                    phoneNumber = "555-678-9101",
                    address = "99999 west business street, Busy, Ness 00000",
                    isExpanded = false,
                    webURL = "https://businessEmail.com",
                    myOpinionRating = 3.5f,
                    daysOpen = booleanArrayOf(true, true, true, true, true, false, false)
                ),
                PersonModel(
                    id = 2,
                    name = "Test Person",
                    email = "",
                    phoneNumber = "555-555-5555",
                    address = "11111 west fake street, Zoo, Narnia 99999",
                    isExpanded = false,
                    pictureURL = null,
                    isFamilyMember = true,
                    birthDate = LocalDate.now(),
                    group = "",
                    isEmergency = false,
                    isFavorite = false
                ),
                ContactModel(
                    3,
                    "Test2",
                    "test2@test.com",
                    "555-556-5555",
                    "11111 east fake street, Aquarium, Narnia 99998",
                    false
                ),
                ContactModel(
                    4,
                    "Test3",
                    "test3@test.com",
                    "555-566-5555",
                    "11111 south fake street, Movie, Narnia 99899",
                    false
                )
            ) )
//        getAllContacts()
    }

//    override fun getAllContacts() {
//        Log.d("ContactJSONImport", "Loading Contacts from JSON")
//        val file = File(appContext.filesDir, "contacts.json")
//
//        val mapper = ObjectMapper()
//        if (file.exists()) {
//            // read the entire list of jokes from the json file.
//            val newContacts = mapper.readValue(file, Array<ContactModel>::class.java).toMutableList()
//            // remove all jokes from the list and add the new jokes
//            contactsList.clear()
//            contactsList.addAll(newContacts)
//            /*
//                Why not do it this way?
//                jokesList = mapper.readValue(file, Array<JokeModel>::class.java).toMutableList()
//                According to the way mutableStateListOf works, we cannot simply assign a new list to jokesList
//                The UI will not be updated. We must replace the items in the list. This triggers the recomposition of the list
//            */
//            Log.d("ViewModelJSON", "Contacts loaded from JSON: ${contactsList.size}")
//        }
//    }

    override fun getAllContacts() {
        Log.d("ContactJSONImport", "Loading Contacts from JSON")
        val file = File(appContext.filesDir, "contacts.json")

        val mapper = ObjectMapper().registerModule(JavaTimeModule())
        mapper.registerModule(com.fasterxml.jackson.databind.module.SimpleModule().addDeserializer(Uri::class.java, UriDeserializer()))

        if (file.exists()) {
            try {
                val typeFactory = TypeFactory.defaultInstance()
                val collectionType = typeFactory.constructCollectionType(MutableList::class.java, ContactModel::class.java)
                val newContacts: MutableList<ContactModel> = mapper.readValue(file, collectionType)

                contactsList.clear()
                contactsList.addAll(newContacts)

                Log.d("ViewModelJSON", "Contacts loaded from JSON: ${contactsList.size}")
            } catch (e: Exception) {
                Log.e("ViewModelJSON", "Error loading contacts: ${e.message}", e)
            }
        }
    }

    override fun addContact(contact: ContactModel) {
        // add the joke to the list and save the list to the JSON file
        contactsList.add(contact)
        saveContacts()
    }

    override fun removeContact(contact: ContactModel) {
        // remove the joke from the list and save the list to the JSON file
        contactsList.remove(contact)
        saveContacts()
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

    override fun hideShowContact(contact: ContactModel) {
        // determine which list to use
        var contactsList = if (contactsSearchResult.isEmpty()) contactsList else contactsSearchResult
        // set all visible to false
        contactsList.forEach{ it.setExpanded(false) }
        // set the contact passed in to true
        contactsList[contactsList.indexOf(contact)] = contact.copy(isExpanded = true)
    }

    override fun saveContacts() {
        // perform the file IO on the IO thread using coroutine to prevent blocking the main thread
        // blocking the main thread can cause the app to freeze and become unresponsive during file IO
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("ViewModelJSON", "Saving contacts to JSON")
            contactsList.forEach { Log.d("ViewModelJSON", "Contact: $it, Type: ${it::class.java.simpleName}") } // Log the types
            val file = File(appContext.filesDir, "contacts.json")
            val mapper = ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .registerModule(JavaTimeModule())
            mapper.writeValue(file, contactsList)
        }
        Log.d("ViewModelJSON", "Contacts saved to JSON: ${contactsList.size}")
    }

    override fun closeAllContacts() {
        // determine which list to use
        var contactsList = if (contactsSearchResult.isEmpty()) contactsList else contactsSearchResult
        // set all visible to false
        contactsList.forEach{ it.setExpanded(false) }
    }

    override fun updateContactPicture(id: Int, uri: Uri?) {
        val contact = contactsList[id]
        if (contact is PersonModel) {
            // Create a new contact with the updated picture
            val updatedContact = contact.copyPersonPicture(pictureURL = uri)

            // Force a state update by replacing the item in the list
            contactsList[id] = updatedContact

            // Save the changes to persistent storage
            saveContacts()

            // Log for debugging
//            Log.d("IMAGE PERSON ORIGIN", contact.toString())
//            Log.d("IMAGE PERSON UPDATE", updatedContact.toString())
//            Log.d("ContactViewModel", "Picture updated for contact $id: ${uri}")
        }
    }

    class UriDeserializer : JsonDeserializer<Uri>() {
        override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): Uri? {
            val uriString = p?.valueAsString
            return if (uriString.isNullOrEmpty()) {
                null
            } else {
                Uri.parse(uriString)
            }
        }
    }
}