package com.dylanjohnson.contactsapp.models

import java.time.LocalDate
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonFormat

class BusinessModel : ContactModel {
    // Class properties
    @JsonProperty("web_url") private var _webURL: String = ""
    @JsonProperty("my_opinion") private var _myOpinionRating: Int = 0
    @JsonProperty("days_open") private var _daysOpen: BooleanArray = BooleanArray(7)
    @JsonIgnore
    private var _productsBought: MutableList<Product>

    // Default (Empty) Constructor
    constructor() {
        this._webURL = ""
        this._myOpinionRating = 0
        this._daysOpen = booleanArrayOf(false, false, false, false, false, false, false)
        this._productsBought = mutableListOf()
    }

    // Secondary (Input) Constructor
    constructor(webURL: String, myOpinionRating: Int,
                daysOpen: BooleanArray, productsBought: MutableList<Product>
    ) {
        this._webURL = webURL
        this._myOpinionRating = myOpinionRating
        this._daysOpen = daysOpen
        this._productsBought = productsBought
    }

    // Overwritten toString method to print in specific format
    override fun toString(): String {
        return "\n\tBusiness Model ${getId()}(\n\t Picture Url: '$_webURL'\n\t My Rating: $_myOpinionRating\n\t " +
                "Days Open: $_daysOpen\n\t Products Bought: $_productsBought\n\t)"
    }

    // Function to add to the products bought array
    fun buyProduct(newVal: Product) {
        _productsBought.add(newVal)
    }

    // Getters
    fun getWebURL() : String {
        return _webURL
    }
    fun getMyOpinionRating() : Int {
        return _myOpinionRating
    }
    fun getDaysOpen() : BooleanArray {
        return _daysOpen
    }
    fun getProductsBought() : MutableList<Product> {
        return _productsBought
    }

    // Setters
    fun setWebURL(newVal : String) {
        _webURL = newVal
    }
    fun setMyOpinionRating(newVal : Int) {
        _myOpinionRating = newVal
    }
    fun setDaysOpen(index : Int, newVal : Boolean) {
        _daysOpen[index] = newVal
    }
    fun setProductsBought(newVal : MutableList<Product>) {
        _productsBought = newVal
    }
}

data class Product (
    private var productName: String,
    private var productPrice: Double,
    private var datePurchased: LocalDate
) {
    override fun toString(): String {
        return "\n\tProduct $productName(\n\t Price: '$productPrice'\n\t " +
                "Date Purchased: $datePurchased\n\t)"
    }
    // Getters
    fun getProductName() : String {
        return productName
    }
    fun getProductPrice() : Double {
        return productPrice
    }
    fun getDatePurchased() : LocalDate {
        return datePurchased
    }

    // Setters
    fun setProductName(newVal : String) {
        productName = newVal
    }
    fun setProductPrice(newVal : Double) {
        productPrice = newVal
    }
    fun setDatePurchased(newVal : LocalDate) {
        datePurchased = newVal
    }
}