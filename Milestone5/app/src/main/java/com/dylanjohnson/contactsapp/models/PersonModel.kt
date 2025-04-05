package com.dylanjohnson.contactsapp.models

import android.net.Uri
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import android.util.Log
import java.time.LocalDate
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonSerialize

@JsonTypeName("PersonModel")
class PersonModel : ContactModel {
    // Class properties
//    @JsonSerialize(using = UriSerializer::class)
    /*@JsonProperty("picture_url")*/ private var _pictureURL: Uri? = null
    /*@JsonProperty("is_family")*/ private var _isFamilyMember: Boolean = false
    /*@JsonProperty("birthday")*/
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private var _birthDate: LocalDate = LocalDate.now()
    /*@JsonProperty("group")*/ private var _group: String = ""
    /*@JsonProperty("emergency")*/ private var _isEmergency: Boolean = false
    /*@JsonProperty("is_favorite")*/ private var _isFavorite: Boolean = false

    // Default (Empty) Constructor
    constructor() {
        this._pictureURL = null
        this._isFamilyMember = false
        this._birthDate = LocalDate.now()
        this._group = ""
        this._isEmergency = false
        this._isFavorite = false
    }

    // Secondary (Input) Constructor
    constructor( id: Int, name: String, email: String,
                 phoneNumber: String, address: String, isExpanded : Boolean,
                 pictureURL: Uri?, isFamilyMember: Boolean,
                 birthDate: LocalDate, group: String,
                 isEmergency: Boolean, isFavorite: Boolean
    ) : super(id, name, email, phoneNumber, address, isExpanded) {
        this._pictureURL = pictureURL
        this._isFamilyMember = isFamilyMember
        this._birthDate = birthDate
        this._group = group
        this._isEmergency = isEmergency
        this._isFavorite = isFavorite
    }

    // Overwritten toString method to print in specific format
    override fun toString(): String {
        return "\n\tPerson Model ${getId()}(\n\t Picture Url: '${_pictureURL.toString()}'\n\t Is Family: $_isFamilyMember\n\t " +
                "Birthday: $_birthDate\n\t Group: $_group\n\t " +
                "Emergency Contact: $_isEmergency\n\t Favorite: $_isFavorite\n\t)"
    }

    // Getters
    fun getPictureURL() : Uri? {
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
    fun setPictureURL(newVal : Uri?) {
        Log.d("IMAGE CONVERT", newVal.toString())
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

    override fun copy(
        id: Int,
        name: String,
        email: String,
        phoneNumber: String,
        address: String,
        isExpanded: Boolean
    ): PersonModel {
        Log.d("COPY", "PERSON MODEL")
        return PersonModel(id, name, email, phoneNumber, address,
            isExpanded, _pictureURL, _isFamilyMember,
            _birthDate, _group, _isEmergency, _isFavorite)
    }

    fun copyPersonPicture(
        pictureURL: Uri?
    ): PersonModel {
        setPictureURL(pictureURL)
        Log.d("IMAGE COPY", super.getName() + ": " + pictureURL.toString())
        return PersonModel(super.getId(), super.getName(), super.getEmail(), super.getPhoneNum(), super.getAddress(),
            super.getExpanded(), getPictureURL(), _isFamilyMember,
            _birthDate, _group, _isEmergency, _isFavorite)
    }

    class UriSerializer : JsonSerializer<Uri>() {
        override fun serialize(value: Uri?, gen: JsonGenerator?, serializers: SerializerProvider?) {
            gen?.writeString(value?.toString() ?: "")
        }
    }
}