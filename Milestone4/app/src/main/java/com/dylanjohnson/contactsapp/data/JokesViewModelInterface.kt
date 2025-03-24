package com.dylanjohnson.contactsapp.data

import com.dylanjohnson.contactsapp.models.ContactModel

interface JokesViewModelInterface {
    var contactsList : MutableList<ContactModel>
    var contactsSearchResult : MutableList<ContactModel>

    fun getAllContacts()

    fun addContact(contact: ContactModel)

    fun removeContact(contact: ContactModel)

    fun findContactsByKeyword(findPhrase: String)

    fun hideShowContact(contact: ContactModel)

    fun saveContacts()
}