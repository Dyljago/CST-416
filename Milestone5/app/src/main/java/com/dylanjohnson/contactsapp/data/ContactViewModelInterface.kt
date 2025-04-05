package com.dylanjohnson.contactsapp.data

import android.net.Uri
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.dylanjohnson.contactsapp.models.ContactModel

interface ContactViewModelInterface {
    var contactsList : SnapshotStateList<ContactModel>
    var contactsSearchResult : SnapshotStateList<ContactModel>

    fun getAllContacts()

    fun addContact(contact: ContactModel)

    fun removeContact(contact: ContactModel)

    fun findContactsByKeyword(findPhrase: String)

    fun hideShowContact(contact: ContactModel)

    fun saveContacts()

    fun closeAllContacts()

    fun updateContactPicture(id: Int, uri: Uri?)
}