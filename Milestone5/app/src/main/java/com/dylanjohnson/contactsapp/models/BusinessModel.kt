package com.dylanjohnson.contactsapp.models

import android.util.Log
import androidx.compose.runtime.remember
import java.time.LocalDate
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonTypeName
import kotlin.math.round

@JsonTypeName("BusinessModel")
class BusinessModel : ContactModel {
    // Class properties
    /*@JsonProperty("web_url")*/ private var _webURL: String = ""
    /*@JsonProperty("my_opinion")*/ private var _myOpinionRating: Float = 0f
    /*@JsonProperty("days_open")*/ private var _daysOpen: BooleanArray = BooleanArray(7)

    // Default (Empty) Constructor
    constructor() {
        this._webURL = ""
        this._myOpinionRating = 0f
        this._daysOpen = booleanArrayOf(false, false, false, false, false, false, false)
    }

    // Secondary (Input) Constructor
    constructor(id: Int, name: String, email: String,
                phoneNumber: String, address: String, isExpanded : Boolean,
                webURL: String, myOpinionRating: Float,
                daysOpen: BooleanArray
    ) : super(id, name, email, phoneNumber, address, isExpanded) {
        this._webURL = webURL
        this._myOpinionRating = myOpinionRating
        this._daysOpen = daysOpen
    }

    // Overwritten toString method to print in specific format
    override fun toString(): String {
        return "\n\tBusiness Model ${getId()}(\n\t Picture Url: '$_webURL'\n\t My Rating: $_myOpinionRating\n\t " +
                "Days Open: $_daysOpen\n\t)"
    }

    // Getters
    fun getWebURL() : String {
        return _webURL
    }
    fun getMyOpinionRating() : Float {
        return _myOpinionRating
    }
    fun getDaysOpen() : BooleanArray {
        return _daysOpen
    }

    // Setters
    fun setWebURL(newVal : String) {
        _webURL = newVal
    }

    private fun roundToNearestHalf(value: Float): Float {
        return (round(value * 2) / 2)
    }

    fun setMyOpinionRating(newVal: Float) {
        _myOpinionRating = if (newVal > 5) {
            5f
        } else {
            roundToNearestHalf(newVal)
        }
    }
    fun setDaysOpen(index : Int, newVal : Boolean) {
        _daysOpen[index] = newVal
    }

    fun getDaysOpenForText(): String {
        val dayNames = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
        val openDays = mutableListOf<String>()

        _daysOpen.forEachIndexed { index, day ->
            if (day) {
                openDays.add(dayNames[index])
            }
        }

        val daysOpenText = if (openDays.isEmpty()) {
            "Days Open: Closed All Week"
        } else {
            "Days Open: ${openDays.joinToString(", ")}"
        }
        return daysOpenText
    }

    override fun copy(
        id: Int,
        name: String,
        email: String,
        phoneNumber: String,
        address: String,
        isExpanded: Boolean
    ): BusinessModel {
        Log.d("COPY", "Business MODEL")
        return BusinessModel(id, name, email, phoneNumber, address,
            isExpanded, _webURL, _myOpinionRating, _daysOpen)
    }
}
