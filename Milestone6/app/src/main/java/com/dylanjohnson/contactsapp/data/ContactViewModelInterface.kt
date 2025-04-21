package com.dylanjohnson.contactsapp.data

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.dylanjohnson.contactsapp.models.ContactModel
import com.dylanjohnson.contactsapp.models.ThemeModel

interface ContactViewModelInterface {
    var contactsList : SnapshotStateList<ContactModel>
    var contactsSearchResult : SnapshotStateList<ContactModel>
    var chosenTheme : MutableState<ThemeModel>

    fun getAllContacts()

    fun addContact(contact: ContactModel)

    fun removeContact(contact: ContactModel)

    fun findContactsByKeyword(findPhrase: String)

    fun hideShowContact(contact: ContactModel)

    fun saveContacts()

    fun closeAllContacts()

    fun updateContactPicture(id: Int, uri: Uri?)

    fun saveTheme()

    fun getTheme()
}