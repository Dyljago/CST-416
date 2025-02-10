package com.dylanjohnson.contactsapp.models

import java.time.LocalDate

class PersonModel : ContactModel {
    // Class properties
    private var _pictureURL: String
    private var _isFamilyMember: Boolean
    private var _birthDate: LocalDate
    private var _group: String
    private var _isEmergency: Boolean
    private var _isFavorite: Boolean

    // Default (Empty) Constructor
    constructor() {
        this._pictureURL = ""
        this._isFamilyMember = false
        this._birthDate = LocalDate.now()
        this._group = ""
        this._isEmergency = false
        this._isFavorite = false
    }

    // Secondary (Input) Constructor
    constructor( pictureURL: String, isFamilyMember: Boolean,
                 birthDate: LocalDate, group: String,
                 isEmergency: Boolean, isFavorite: Boolean
    ) {
        this._pictureURL = pictureURL
        this._isFamilyMember = isFamilyMember
        this._birthDate = birthDate
        this._group = group
        this._isEmergency = isEmergency
        this._isFavorite = isFavorite
    }

    // Overwritten toString method to print in specific format
    override fun toString(): String {
        return "\n\tPerson Model ${getId()}(\n\t Picture Url: '$_pictureURL'\n\t Is Family: $_isFamilyMember\n\t " +
                "Birthday: $_birthDate\n\t Group: $_group\n\t " +
                "Emergency Contact: $_isEmergency\n\t Favorite: $_isFavorite\n\t)"
    }

    // Getters
    fun getPictureURL() : String {
        return _pictureURL
    }
    fun getFamilyMember() : Boolean {
        return _isFamilyMember
    }
    fun getBirthDate() : LocalDate {
        return _birthDate
    }
    fun getGroup() : String {
        return _group
    }
    fun getEmergencyContact() : Boolean {
        return _isEmergency
    }
    fun getFavorite() : Boolean {
        return _isFavorite
    }

    // Setters
    fun setPictureURL(newVal : String) {
        _pictureURL = newVal
    }
    fun setIsFamily(newVal : Boolean) {
        _isFamilyMember = newVal
    }
    fun setBirthDate(newVal : LocalDate) {
        _birthDate = newVal
    }
    fun setGroup(newVal : String) {
        _group = newVal
    }
    fun setIsEmergencyContact(newVal : Boolean) {
        _isEmergency = newVal
    }
    fun setIsFavorite(newVal: Boolean) {
        _isFavorite = newVal
    }
}