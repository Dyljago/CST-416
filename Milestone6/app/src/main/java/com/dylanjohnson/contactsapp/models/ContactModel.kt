package com.dylanjohnson.contactsapp.models

import android.util.Log
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName


@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME, // Use the name of the subclass as the identifier
    include = JsonTypeInfo.As.PROPERTY, // Include type info as a property in JSON
    property = "type", // The key that will hold the type name
    defaultImpl = ContactModel::class
)
//@JsonSubTypes(
//    JsonSubTypes.Type(value = PersonModel::class, name = "PersonModel"),
//    JsonSubTypes.Type(value = BusinessModel::class, name = "BusinessModel"),
//)

@JsonSubTypes(
    JsonSubTypes.Type(value = PersonModel::class, name = "com.dylanjohnson.contactsapp.models.PersonModel"),
    JsonSubTypes.Type(value = BusinessModel::class, name = "com.dylanjohnson.contactsapp.models.BusinessModel")
)

@JsonTypeName("ContactModel")
open class ContactModel {
    // Class properties
    private var _id: Int
    private var _name: String
    private var _email: String
    private var _phoneNumber: String
    private var _address: String
    private var _isExpanded: Boolean

    // Default (Empty) Constructor
    constructor() {
        this._id = 0
        this._name = "Unknown"
        this._email = ""
        this._phoneNumber = ""
        this._address = ""
        this._isExpanded = false
    }

    // Secondary (Input) Constructor
    @JsonCreator
    constructor( @JsonProperty("id")id: Int, @JsonProperty("name")name: String, @JsonProperty("email")email: String,
                 @JsonProperty("phoneNum")phoneNumber: String, @JsonProperty("address")address: String,
                 @JsonProperty("expanded")isExpanded : Boolean,
    ) {
        this._id = id
        this._name = name
        this._email = email
        this._phoneNumber = phoneNumber
        this._address = address
        this._isExpanded = isExpanded
    }

    // Overwritten toString method to print in specific format
    override fun toString(): String {
        return "\n\tContact Model $_id(\n\t Name: '$_name'\n\t Email Address: $_email\n\t " +
                "Phone Number: $_phoneNumber\n\t Address: $_address\n\t Expanded: $_isExpanded\n\t)"
    }

    // Getters
    fun getId() : Int {
        return _id
    }
    fun getName() : String {
        return _name
    }
    fun getEmail() : String {
        return _email
    }
    fun getPhoneNum() : String {
        return _phoneNumber
    }
    fun getAddress() : String {
        return _address
    }
    fun getExpanded() : Boolean {
        return _isExpanded
    }

    // Setters
    fun setID(newId: Int) {
        _id = newId
    }
    fun setName(newVal : String) {
        _name = newVal
    }
    fun setEmail(newVal : String) {
        _email = newVal
    }
    fun setPhoneNum(newVal : String) {
        _phoneNumber = newVal
    }
    fun setAddress(newVal : String) {
        _address = newVal
    }
    fun setExpanded(newVal : Boolean) {
        _isExpanded = newVal
    }

    open fun copy(
        id: Int = _id,
        name: String = _name,
        email: String = _email,
        phoneNumber: String = _phoneNumber,
        address: String = _address,
        isExpanded: Boolean = _isExpanded
    ): ContactModel {
        //Log.d("COPY", "Contact MODEL")
        return ContactModel(id, name, email, phoneNumber, address, isExpanded)
    }

}