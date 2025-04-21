package com.dylanjohnson.contactsapp.models

import android.net.Uri
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import android.util.Log
import com.fasterxml.jackson.annotation.JsonCreator
import java.time.LocalDate
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer

@JsonTypeName("PersonModel")
class PersonModel : ContactModel {
    private var _birthDate: LocalDate? = null
    // Class properties
//    @JsonSerialize(using = UriSerializer::class)
    /*@JsonProperty("picture_url")*/
    @JsonIgnore private var _pictureURL: Uri? = null
    private var _picturePath: String = ""
    /*@JsonProperty("is_family")*/ private var _isFamilyMember: Boolean = false
    /*@JsonProperty("birthday")*/
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
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
    @JsonCreator
    constructor( @JsonProperty("id") id: Int, @JsonProperty("name")name: String, @JsonProperty("email")email: String,
                 @JsonProperty("phoneNum")phoneNumber: String, @JsonProperty("address")address: String,
                 @JsonProperty("expanded")isExpanded : Boolean, @JsonProperty("picturePath")picturePath: String,
                 @JsonProperty("familyMember")isFamilyMember: Boolean,
                 @JsonDeserialize(using = LocalDateDeserializer::class) @JsonProperty("birthDate")birthDate: LocalDate?,
                 @JsonProperty("group")group: String,
                 @JsonProperty("emergencyContact")isEmergency: Boolean, @JsonProperty("favorite")isFavorite: Boolean
    ) : super(id, name, email, phoneNumber, address, isExpanded) {
        this._picturePath = picturePath
        if (picturePath == null || picturePath.isEmpty()) {
            this._pictureURL = null
        } else {
//            Log.d("LOADING IMAGE", picturePath)
            this._pictureURL = Uri.parse(picturePath)
//            Log.d("LOADING IMAGE2", this._pictureURL.toString())
        }
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
    @JsonIgnore
    fun getPictureURL() : Uri? {
        return _pictureURL
    }
    fun getPicturePath() : String {
        return _picturePath
    }
    fun getFamilyMember() : Boolean {
        return _isFamilyMember
    }
    fun getBirthDate() : LocalDate? {
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
        //Log.d("IMAGE CONVERT", newVal.toString())
        _pictureURL = newVal
        if (newVal != null) {
            _picturePath = newVal.toString()
        } else {
            _picturePath = ""
        }
    }
    fun setIsFamily(newVal : Boolean) {
        _isFamilyMember = newVal
    }
    fun setBirthDate(newVal : LocalDate?) {
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
        //Log.d("COPY", "PERSON MODEL")
        return PersonModel(id, name, email, phoneNumber, address,
            isExpanded, _picturePath, _isFamilyMember,
            _birthDate, _group, _isEmergency, _isFavorite)
    }

    fun personCopy(contact: PersonModel): PersonModel {
        return PersonModel(contact.getId(), contact.getName(), contact.getEmail(),
            contact.getPhoneNum(), contact.getAddress(), contact.getExpanded(),
            contact.getPicturePath(), contact.getFamilyMember(),
            contact.getBirthDate(), contact.getGroup(), contact.getEmergencyContact(),
            contact.getFavorite())
    }

    fun copyPersonPicture(
        pictureURL: Uri?
    ): PersonModel {
        setPictureURL(pictureURL)
        //Log.d("IMAGE COPY", super.getName() + ": " + pictureURL.toString())
        return PersonModel(super.getId(), super.getName(), super.getEmail(), super.getPhoneNum(), super.getAddress(),
            super.getExpanded(), getPicturePath(), _isFamilyMember,
            _birthDate, _group, _isEmergency, _isFavorite)
    }
}