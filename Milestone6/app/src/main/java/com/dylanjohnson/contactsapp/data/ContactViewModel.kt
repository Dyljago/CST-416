package com.dylanjohnson.contactsapp.data

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dylanjohnson.contactsapp.models.BusinessModel
import com.dylanjohnson.contactsapp.models.ContactModel
import com.dylanjohnson.contactsapp.models.PersonModel
import com.dylanjohnson.contactsapp.models.ThemeModel
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
import com.fasterxml.jackson.databind.jsontype.NamedType
import androidx.lifecycle.viewModelScope
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator
import com.fasterxml.jackson.module.kotlin.KotlinModule
import kotlinx.coroutines.launch

class ContactViewModel (context: Context) : ContactViewModelInterface, ViewModel() {
    val appContext = context
    override var contactsList = mutableStateListOf<ContactModel>()
    override var contactsSearchResult = mutableStateListOf<ContactModel>()
    override var chosenTheme: MutableState<ThemeModel> = mutableStateOf(ThemeModel())

    init {
//        initialContacts()
        getTheme()
        getAllContacts()
    }

    private fun initialContacts() {
        // Set contacts
        contactsList.addAll( listOf(
                PersonModel(
                    id = 0,
                    name = "Person Example",
                    email = "test@test.com",
                    phoneNumber = "555-555-5555",
                    address = "11111 east fake street, Zoo, Narnia 99999",
                    isExpanded = false,
                    picturePath = "",
                    isFamilyMember = true,
                    birthDate = LocalDate.now(),
                    group = "Family",
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
                    picturePath = "",
                    isFamilyMember = true,
                    birthDate = LocalDate.now(),
                    group = "",
                    isEmergency = false,
                    isFavorite = false
                ),
                ContactModel(
                    3,
                    "Test3",
                    "test3@test.com",
                    "555-566-5555",
                    "11111 south fake street, Movie, Narnia 99899",
                    false
                ),
                PersonModel(
                    id = 4,
                    name = "Test2",
                    email = "test2@test.com",
                    phoneNumber = "555-567-5555",
                    address = "11111 north fake street, Aquarium, Narnia 95999",
                    isExpanded = false,
                    picturePath = "",
                    isFamilyMember = false,
                    birthDate = LocalDate.now(),
                    group = "",
                    isEmergency = false,
                    isFavorite = true
                )
            ) )
    }

    override fun getAllContacts() {
        //Log.d("ContactJSONImport", "Loading Contacts from JSON")
        val file = File(appContext.filesDir, "contacts.json")

        val mapper = ObjectMapper().registerModule(JavaTimeModule())
        if (file.exists()) {
            try {
                val typeFactory = mapper.typeFactory
                val collectionType = typeFactory.constructCollectionType(
                    MutableList::class.java,
                    ContactModel::class.java // Deserialize into the base class
                )
                val newContacts: MutableList<ContactModel> = mapper.readValue(file, collectionType)
                contactsList.clear()
                contactsList.addAll(newContacts)
                //Log.d("ViewModelJSON", "Contacts loaded from JSON: ${contactsList.size}")
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
        // Remove the contact from the list
        contactsList.remove(contact)

        // Re-index the remaining contacts
        reindexContacts()

        // Save the updated list to the JSON file
        saveContacts()
    }

    private fun reindexContacts() {
        val newContactsList = mutableListOf<ContactModel>()
        contactsList.forEachIndexed { index, contact ->
            val updatedContact = contact.copy(id = index)
            newContactsList.add(updatedContact)
        }
        contactsList.clear() // Clear the original list
        contactsList.addAll(newContactsList) // Add the re-indexed contacts back
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
//            Log.d("ViewModelJSON", "Saving contacts to JSON")
//            contactsList.forEach { Log.d("ViewModelJSON", "Contact: $it, Type: ${it::class.java.simpleName}") } // Log the types
            val file = File(appContext.filesDir, "contacts.json")
            val mapper = ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .registerModule(JavaTimeModule()) // so LocalDate serializes correctly
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false) // optional: better date format
                .activateDefaultTypingAsProperty(
                    BasicPolymorphicTypeValidator.builder().allowIfSubType(ContactModel::class.java).build(),
                    ObjectMapper.DefaultTyping.NON_FINAL,
                    "type"
                )
            val closedContacts = contactsList
            for (contact in closedContacts) {
                contact.setExpanded(false)
            }
            mapper.writeValue(file, closedContacts)
        }
//        Log.d("ViewModelJSON", "Contacts saved to JSON: ${contactsList.size}")
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

    override fun saveTheme() {
        // perform the file IO on the IO thread using coroutine to prevent blocking the main thread
        // blocking the main thread can cause the app to freeze and become unresponsive during file IO
        viewModelScope.launch(Dispatchers.IO) {
//            Log.d("THEMEJSONImport", "Saving theme to JSON")
            val file = File(appContext.filesDir, "theme.json")
            val mapper = ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .registerModule(JavaTimeModule())
            mapper.writeValue(file, chosenTheme.value)
        }
//        Log.d("THEMEJSONImport", "Theme saved to JSON")
    }

    override fun getTheme() {
//        Log.d("THEMEJSONImport", "Loading THEME from JSON")
        val file = File(appContext.filesDir, "theme.json")

        val mapper = ObjectMapper().registerModule(JavaTimeModule())
        mapper.registerModule(com.fasterxml.jackson.databind.module.SimpleModule())

        if (file.exists()) {
            try {
                val newTheme: ThemeModel? = mapper.readValue(file, ThemeModel::class.java)

                if (newTheme != null) {
                    chosenTheme.value = newTheme
//                    Log.d("THEMEJSONImport", "Theme loaded from JSON: ${newTheme}")
                } else {
                    chosenTheme.value = ThemeModel()
//                    Log.w("THEMEJSONImport", "Theme file is empty or invalid.")
                }
            } catch (e: Exception) {
                Log.e("THEMEJSONImport", "Error loading theme: ${e.message}", e)
            }
        }
    }
}