package com.dylanjohnson.contactsapp.models

open class ContactModel {
    // Class properties
    private val _id: Int
    private var _name: String
    private var _email: String
    private var _phoneNumber: String
    private var _addressStreet: String
    private var _addressCity: String
    private var _addressState: String
    private var _addressZipCode: String
    private var _isExpanded: Boolean

    // Default (Empty) Constructor
    constructor() {
        this._id = 0
        this._name = "Unknown"
        this._email = ""
        this._phoneNumber = ""
        this._addressStreet = ""
        this._addressCity = ""
        this._addressState = ""
        this._addressZipCode = ""
        this._isExpanded = false
    }

    // Secondary (Input) Constructor
    constructor( id: Int, name: String, email: String,
                 phoneNumber: String, addressStreet: String,
                 addressCity: String, addressState: String,
                 addressZipCode: String, isExpanded : Boolean
    ) {
        this._id = id
        this._name = name
        this._email = email
        this._phoneNumber = phoneNumber
        this._addressStreet = addressStreet
        this._addressCity = addressCity
        this._addressState = addressState
        this._addressZipCode = addressZipCode
        this._isExpanded = isExpanded
    }

    // Overwritten toString method to print in specific format
    override fun toString(): String {
        return "\n\tContact Model $_id(\n\t Name: '$_name'\n\t Email Address: $_email\n\t " +
                "Phone Number: $_phoneNumber\n\t Address: $_addressStreet\n\t\t " +
                "$_addressCity, $_addressState, $_addressZipCode\n\t Expanded: $_isExpanded\n\t)"
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
    fun getAddressStreet() : String {
        return _addressStreet
    }
    fun getAddressCity() : String {
        return _addressCity
    }
    fun getAddressState() : String {
        return _addressState
    }
    fun getAddressZipCode() : String {
        return _addressZipCode
    }
    fun getAddress() : String {
        return "$_addressStreet\n$_addressCity, $_addressState \n$_addressZipCode"
    }
    fun getExpanded() : Boolean {
        return _isExpanded
    }

    // Setters
    fun setName(newVal : String) {
        _name = newVal
    }
    fun setEmail(newVal : String) {
        _email = newVal
    }
    fun setPhoneNum(newVal : String) {
        _phoneNumber = newVal
    }
    fun setAddressStreet(newVal : String) {
        _addressStreet = newVal
    }
    fun setAddressCity(newVal : String) {
       _addressCity = newVal
    }
    fun setAddressState(newVal : String) {
        _addressState = newVal
    }
    fun setAddressZipCode(newVal : String) {
        _addressZipCode = newVal
    }
    fun setExpanded(newVal : Boolean) {
        _isExpanded = newVal
    }

    fun copy(
        id: Int = _id,
        name: String = _name,
        email: String = _email,
        phoneNumber: String = _phoneNumber,
        addressStreet: String = _addressStreet,
        addressCity: String = _addressCity,
        addressState: String = _addressState,
        addressZipCode: String = _addressZipCode,
        isExpanded: Boolean = _isExpanded
    ): ContactModel {
        return ContactModel(id, name, email, phoneNumber, addressStreet, addressCity, addressState, addressZipCode, isExpanded)
    }

}